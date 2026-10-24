package com.sagun.expense_tracker_api.principal;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements UserDetails {

    private final long id;

    public long getId(){
        return id;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    private final String username;
    private final String email;
    private final String passwordHash;

    private final boolean enabled;

    public String getEmail(){
        return email;
    }

    private final List<SimpleGrantedAuthority> authorities;

    private UserPrincipal(Long id, String username, String email,String passwordHash,boolean enabled,List<SimpleGrantedAuthority>authorities){
        this.id  = id;
        this.username = username;
        this.email=email;
        this.enabled=enabled;
        this.passwordHash = passwordHash;
        this.authorities=authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean equals(Object o){
        return this == o && ( o instanceof UserPrincipal other && Objects.equals(id,other.id));
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
}
