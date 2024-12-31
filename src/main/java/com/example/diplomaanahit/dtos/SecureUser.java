package com.example.diplomaanahit.dtos;

import com.example.diplomaanahit.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link javax.swing.text.html.parser.Entity}
 */
public class SecureUser implements UserDetails {

    private UserEntity userEntity;
    private Collection<GrantedAuthority> grantedAuthorities;

    public SecureUser() {
        super();
    }

    public SecureUser(UserEntity userEntity, Collection<GrantedAuthority> grantedAuthorities) {
        this.userEntity = userEntity;
        this.grantedAuthorities = grantedAuthorities;
    }

    public Integer getId() {
        return userEntity.getId();
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public String getUsername() {
        return userEntity.getEmail();
    }

    @JsonIgnore
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

