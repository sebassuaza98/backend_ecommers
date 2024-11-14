package com.ecomers.ecommerce.login.services;

public class UserContext {
    private static Long userId;

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        UserContext.userId = userId;
    }
}
