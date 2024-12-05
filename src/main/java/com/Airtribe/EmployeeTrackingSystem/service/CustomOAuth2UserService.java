package com.Airtribe.EmployeeTrackingSystem.service;


import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.entity.Role;
import com.Airtribe.EmployeeTrackingSystem.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    @Autowired
    private EmployeeRepo employeeRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oauth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");

        if (email == null) {
            throw new IllegalArgumentException("Email not provided by Google.");
        }

        String name = (String) attributes.getOrDefault("name", "Unknown");

//        String firstName = (String) attributes.getOrDefault("given_name", "Unknown");
//        String lastName = (String) attributes.getOrDefault("family_name", "Unknown");

        String tokenValue = userRequest.getAccessToken().getTokenValue();
        System.out.println("JWT Token: " + tokenValue);


        String idToken = userRequest.getAdditionalParameters().get("id_token") != null
                ? userRequest.getAdditionalParameters().get("id_token").toString()
                : null;

        if (idToken != null) {
            System.out.println("Bearer " + idToken);

        } else {
            System.out.println("ID Token not available in the response.");
        }


        Employee employee = employeeRepository.findByEmployeeEmail(email).orElseGet(() -> {
            Employee newEmployee = new Employee();
            newEmployee.setEmployeeEmail(email);
            newEmployee.setEmployeeName(name);

            newEmployee.setRole(Role.EMPLOYEE);


            if (name.equalsIgnoreCase("Admin")) {
                newEmployee.setRole(Role.ADMIN);
            }
            else if(name.equalsIgnoreCase("Manager")){
                newEmployee.setRole(Role.MANAGER);
            }
            else {
                newEmployee.setRole(Role.EMPLOYEE);
            }

            return employeeRepository.save(newEmployee);
        });


        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + employee.getRole().name());

        return new DefaultOAuth2User(List.of(authority), attributes, "email");
    }
}