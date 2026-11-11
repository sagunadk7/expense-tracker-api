package com.sagun.expense_tracker_api.principal;

import com.sagun.expense_tracker_api.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private final long id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final boolean enabled;
    private final List<SimpleGrantedAuthority> authorities;

    private UserPrincipal(Long id, String username, String email, String passwordHash, boolean enabled, List<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.passwordHash = passwordHash;
        this.authorities = authorities;
    }

    public static UserPrincipal from(User user) {
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.isEnabled(),
                List.copyOf(authorities)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPrincipal other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
