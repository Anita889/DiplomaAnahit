package com.example.diplomaanahit.security;

import com.example.diplomaanahit.enums.UserType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String role;
        if(username.contains("student")){
            role = UserType.ADMIN.getLabel();
        }
        else if(username.contains("admin")){
            role = UserType.ADMIN.getLabel();
        }
        else if (username.contains("lecturer")){
            role = UserType.LECTURER.getLabel();
        }
        else {
            role = UserType.USER.getLabel();
        }
            return User.builder()
                    .username(username)
                    .password("{noop}password") // {noop} indicates no encoding
                    .roles(role)
                    .build();
    }
}