package com.ecomers.ecommerce.login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.login.models.Users;
import com.ecomers.ecommerce.login.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
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
    }
}
