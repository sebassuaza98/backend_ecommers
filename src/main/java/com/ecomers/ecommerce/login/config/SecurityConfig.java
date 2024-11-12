package com.ecomers.ecommerce.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.login.models.Users;
import com.ecomers.ecommerce.login.repository.UserRepository;


import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/register", "/auth/login","/api/products","/api/getProducts","/api/update","/orders/create","/orders/top","/orders/reports").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> 
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        return authenticationManager;
    }

    @Bean
public UserDetailsService userDetailsService() {
    return userId -> {
        Long id;
        try {
            id = Long.parseLong(userId);  
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException(Constants.INVALID_USER + userId);
        }

        Users user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(Constants.NOT_FOUND + userId);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getId().toString())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    };
}
}
