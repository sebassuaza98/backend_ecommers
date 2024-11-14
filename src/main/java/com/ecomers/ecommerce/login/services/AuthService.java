package com.ecomers.ecommerce.login.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.login.models.LoginResponse;
import com.ecomers.ecommerce.login.models.Session;
import com.ecomers.ecommerce.login.models.Users;
import com.ecomers.ecommerce.login.repository.SessionRepository;
import com.ecomers.ecommerce.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Users register(Users user) {
        if (userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException(Constants.USERAUDT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginResponse login(Long userId, String password) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException(Constants.USERNOT);
        }
    
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException(Constants.INCORRECT);
        }
    
        UserContext.setUserId(userId);
    
        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.JWT_EXPIRATION))
                .sign(Algorithm.HMAC256(Constants.JWT_SECRET));
    
        Session session = new Session();
        session.setToken(token);
        session.setUserId(user.getId());
        sessionRepository.save(session);

        return new LoginResponse(token, user.getName(), user.getId());
    }
    
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.deleteById(userId); 
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

}
