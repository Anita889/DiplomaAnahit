package com.example.diplomaanahit.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

public class UserAuthProvider extends DaoAuthenticationProvider {

    public boolean supports(Class<?> authentication) {
        return (UserAuthToken.class.equals(authentication));
    }
}

