package com.Airtribe.EmployeeTrackingSystem.config;

import com.Airtribe.EmployeeTrackingSystem.service.CustomOAuth2UserService;
import com.Airtribe.EmployeeTrackingSystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private RoleService roleService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("Configuring http filterChain");

        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/departments/**").hasRole("ADMIN")
                        .requestMatchers("/projects/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/employee/**").hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF if not needed

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();


        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {

            String email = jwt.getClaimAsString("email");
            if (email == null) {
                throw new RuntimeException("Email claim is missing in the JWT.");
            }


            List<String> roles = roleService.getRolesByEmail(email);

            if (roles == null || roles.isEmpty()) {
                throw new RuntimeException("No roles found for user: " + email);
            }

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });

        return jwtAuthenticationConverter;
    }


}
