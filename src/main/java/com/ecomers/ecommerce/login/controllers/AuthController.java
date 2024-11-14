package com.ecomers.ecommerce.login.controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecomers.ecommerce.Util.ApiResponse;
import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.login.models.LoginRequest;
import com.ecomers.ecommerce.login.models.LoginResponse;
import com.ecomers.ecommerce.login.models.UserIdRequest;
import com.ecomers.ecommerce.login.models.Users;
import com.ecomers.ecommerce.login.services.AuthService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Users user) {
        try {
            Users registeredUser = authService.register(user);
        
            ApiResponse response = new ApiResponse(
                HttpStatus.CREATED.value(), Constants.USER,
                registeredUser
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Constants.ERROR,
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest.getUserId(), loginRequest.getPassword());
            
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                Constants.AUTH,
                Map.of(
                    "token", loginResponse.getToken(),
                    "username", loginResponse.getUsername(),
                    "userId", loginResponse.getUserId()  
                )
            );
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserIdRequest request) {
        try {
            authService.deleteUser(request.getUserId());
            return ResponseEntity.ok(Constants.DELETE);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @GetMapping("/getUsers")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = authService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
}

