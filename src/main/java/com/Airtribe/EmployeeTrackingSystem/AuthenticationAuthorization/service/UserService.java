package com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.service;
import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.entity.User;
import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.jwtUtil.JwtUtil;
import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.model.UserModel;
import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserModel userModel) {
        User dbUser = new User();
        dbUser.setUsername(userModel.getUsername());
        dbUser.setPassword(passwordEncoder.encode(userModel.getPassword()));

        if(dbUser.getUsername().contains("@admin"))
            dbUser.setRole("ADMIN");
        else if(dbUser.getUsername().contains("@manager"))
            dbUser.setRole("MANAGER");
        else
            dbUser.setRole("EMPLOYEE");

        return userRepository.save(dbUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    public String signIn(String username, String password) {
        boolean isAuthenticated = authenticateUser(username, password);
        if (isAuthenticated) {
            return JwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    private boolean authenticateUser(String username, String password) {
        Optional<User> fetchedUser = userRepository.findByUsername(username);

        if (fetchedUser == null || fetchedUser.isEmpty()) {
            return false;
        }

        boolean doesPasswordMatch = passwordEncoder.matches(password, fetchedUser.get().getPassword());
        if (!doesPasswordMatch) {
            return false;
        }

        UserDetails userDetails = loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;

    }

}
