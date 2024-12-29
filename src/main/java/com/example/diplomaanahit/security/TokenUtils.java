package com.example.diplomaanahit.security;

import com.example.diplomaanahit.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by garik
 */
@Component
public class TokenUtils {

    private static final String USER_TYPE = "user-type";


    public static String getUsernameFromToken(String token, String secret) {
        String username;
        try {
            username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public static UserType getUserTypeFromToken(String token, String secret) {
        String type;
        UserType t;
        try {
            type = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody().get(USER_TYPE, String.class);
            t = UserType.getByLabel(type);

        } catch (Exception e) {
            t = null;
        }
        return t;
    }

    private static Date getExpirationDate(String token, String secret) {
        Date expiration;
        try {
            expiration = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public static String generateToken(UserDetails userDetails, UserType userType, String secret, long expiration) {
        Claims customClaims = Jwts.claims();
        customClaims.put(USER_TYPE, userType);
        return Jwts.builder().setClaims(customClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public TokenUser getTokenUser(String token, String secret){
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);

            String username = claims.getBody().getSubject();
            UserType userType = UserType.getByLabel(claims.getBody().get(USER_TYPE, String.class));
            Date date = claims.getBody().getExpiration();

            return new TokenUser(username, userType, LocalDateTime.now());
        }
        catch (Exception exp){
            return null;
        }
    }


    public static boolean validateToken(String token, UserDetails userDetails, String secret) {
        final String username = getUsernameFromToken(token, secret);
        final Date expiration = getExpirationDate(token, secret);
        return username.equals(userDetails.getUsername()) &&
                expiration.after(new Date(System.currentTimeMillis()));
    }
}

