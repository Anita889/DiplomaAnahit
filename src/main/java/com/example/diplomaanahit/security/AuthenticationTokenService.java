package com.example.diplomaanahit.security;

import com.example.diplomaanahit.enums.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {


    @Value("${security.token.header}")
    private String tokenHeader;

    @Value("${security.token.secret}")
    private String secret;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private TokenUtils tokenUtils;

    public void authenticate(HttpServletRequest request, HttpServletResponse response) {
        String authToken = request.getHeader(tokenHeader);
        if (authToken != null) {
            try {
                TokenUser tokenUser = tokenUtils.getTokenUser(authToken, secret);
                if(tokenUser != null) {
                    String username = tokenUser.getUsername();
                    UserType userType = tokenUser.getUserType();
                    if ((userType == UserType.LECTURER && tokenUser.validToken(UserType.LECTURER)) ||
                            (userType == UserType.STUDENT && tokenUser.validToken(UserType.STUDENT)) ||
                            (userType == UserType.ADMIN && tokenUser.validToken(UserType.ADMIN))) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UserAuthToken userAuthToken = new UserAuthToken(userDetails, null, userDetails.getAuthorities());
                        userAuthToken.setDetails(userDetails);
                        SecurityContextHolder.getContext().setAuthentication(userAuthToken);
                    }
                }
            } catch (Exception exp) {
            }
        }
    }

}

