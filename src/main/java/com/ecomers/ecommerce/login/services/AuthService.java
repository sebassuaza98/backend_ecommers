package com.ecomers.ecommerce.login.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.login.models.Session;
import com.ecomers.ecommerce.login.models.Users;
import com.ecomers.ecommerce.login.repository.SessionRepository;
import com.ecomers.ecommerce.login.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(Long userId, String password) {
        Users user = userRepository.findById(userId).orElse(null);
    
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
    
        Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET);
        String token = JWT.create()
                .withSubject(user.getId().toString()) 
                .withClaim("role", user.getRole())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.JWT_EXPIRATION))
                .sign(algorithm);
    
        Session session = new Session();
        session.setToken(token);
        session.setUserId(user.getId());
        sessionRepository.save(session);
    
        return token;
    }
    
    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
