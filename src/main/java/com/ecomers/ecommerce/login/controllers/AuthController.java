package com.ecomers.ecommerce.login.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.ecomers.ecommerce.Util.ApiResponse;
import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.login.models.Users;
import com.ecomers.ecommerce.login.services.AuthService;

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
                HttpStatus.CREATED.value(),Constants.USER,
                registeredUser
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                Constants.ERROR,
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/login")
public ResponseEntity<Object> login(@RequestParam Long userId, @RequestParam String password) {
    try {
        String token = authService.login(userId, password);
        ApiResponse response = new ApiResponse(
            HttpStatus.OK.value(),Constants.AUTH,
            token
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
        ApiResponse errorResponse = new ApiResponse(
            HttpStatus.BAD_REQUEST.value(),Constants.AUTH_ERR,
            null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}


    @GetMapping("/validate")
public boolean validateToken(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no proporcionado o inválido");
    }
    String token = authorizationHeader.substring(7);
    return authService.validateToken(token);
}

}

