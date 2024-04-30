package com.techartistry.accountservice.security.domain.model;

import com.techartistry.accountservice.user.domain.entities.User;
import com.techartistry.accountservice.security.infrastructure.util.Util;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Clase que representa al usuario autenticado
 */
public class UserPrincipal implements UserDetails {
    @Getter
    private final Long userId;
    private final String email;
    private final String password;
    private final boolean isVerified;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor para crear un UserPrincipal (LOCAL AUTH)
     * @param user Datos del usuario
     */
    public UserPrincipal(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isVerified = user.isVerified();
        this.authorities = Util.mapRolesToAuthorities(user.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isVerified;
    }
}
