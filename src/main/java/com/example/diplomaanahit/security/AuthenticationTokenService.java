package com.example.diplomaanahit.security;

import com.example.diplomaanahit.dtos.AuthDTO;
import com.example.diplomaanahit.dtos.SecureUser;
import com.example.diplomaanahit.dtos.UserDTO;
import com.example.diplomaanahit.entities.UserEntity;
import com.example.diplomaanahit.enums.UserType;
import com.example.diplomaanahit.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {

    @Value("${security.token.header}")
    private String tokenHeader;

    @Value("${security.token.secret}")
    private  String accessTokenSecret;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private TokenUtils tokenUtils;

    public static AuthDTO login(UserEntity userEntity, UserMapper userMapper, String accessTokenSecret) throws Exception {
        UserDetails userDetails = new SecureUser(userEntity, null);
        UserType role;
        Long id = null;
        if(userEntity.getLecturer() != null) {
            role = UserType.LECTURER;
            id= userEntity.getLecturer().getId();
        } else if (userEntity.getStudent() != null) {
            role = UserType.STUDENT;
            id = userEntity.getStudent().getId();
        } else if (userEntity.getAdmin() != null) {
            role = UserType.ADMIN;
            id = userEntity.getAdmin().getId();
        } else {
            role = UserType.USER;
        }
        String accessToken = TokenUtils.generateToken(userDetails, role, accessTokenSecret, 3600);
        UserDTO userDTO = userMapper.userDTOFromUserEntity(userEntity);
        userDTO.setUserId(userDTO.getId());
        userDTO.setId(id);
        userDTO.setRegistrationType(role.getLabel());
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUser(userDTO);
        authDTO.setAccessToken(accessToken);

        return authDTO;
    }


    public void authenticate(HttpServletRequest request, HttpServletResponse response) {
        String authToken = request.getHeader(tokenHeader);
        if (authToken != null) {
            try {
                TokenUser tokenUser = tokenUtils.getTokenUser(authToken, accessTokenSecret);
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

