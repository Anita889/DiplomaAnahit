package com.example.diplomaanahit.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthToken extends UsernamePasswordAuthenticationToken {

    public UserAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserAuthToken(Object principal, Object credentials,
                         Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}

