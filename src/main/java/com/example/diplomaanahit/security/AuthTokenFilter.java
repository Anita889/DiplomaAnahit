package com.example.diplomaanahit.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class AuthTokenFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationTokenService authenticationTokenService;

    public AuthTokenFilter(AuthenticationTokenService authenticationTokenService) {
        this.authenticationTokenService = authenticationTokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        doInnerFilterBO(httpRequest, httpResponse);
        chain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }


    private void doInnerFilterBO(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        authenticationTokenService.authenticate(request, response);
    }
}

