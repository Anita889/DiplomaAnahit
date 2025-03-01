package com.example.diplomaanahit.config;

import com.example.diplomaanahit.security.AuthTokenFilter;
import com.example.diplomaanahit.security.AuthenticationTokenService;
import com.example.diplomaanahit.security.CorsFilter;
import com.example.diplomaanahit.security.CustomUserDetailsService;
import com.example.diplomaanahit.security.EntryPointUnauthorizedHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final EntryPointUnauthorizedHandler unauthorizedHandler;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationTokenService authenticationTokenService;
    //private final UserSecurityService userSecurityService;

    @Value("${security.token.header}")
    private String tokenHeader;

    @Value("${security.refreshtoken.header}")
    private String refreshTokenHeader;

    public SecurityConfig(EntryPointUnauthorizedHandler unauthorizedHandler,
                          CustomUserDetailsService userDetailsService,
                          AuthenticationTokenService authenticationTokenService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailsService = userDetailsService;
        this.authenticationTokenService = authenticationTokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthTokenFilter authenticationTokenFilter = new AuthTokenFilter(authenticationTokenService);
        CorsFilter corsFilter = new CorsFilter(tokenHeader, refreshTokenHeader);

        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/user/authentication/**",
                                "api/students/**",
                                "api/lecturers/**",
                                "/api/user/health/**",
                                "/api/test/**",
                                // Swagger endpoints
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
