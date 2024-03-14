package com.example.pixel.server.authorization.dto;

import com.example.pixel.server.authorization.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Stream;

public final class AuthenticatedUser extends AbstractAuthenticationToken {

    public final User user;

    public final Set<String> scopes;

    public AuthenticatedUser(User user, Set<String> scopes) {
        super(Stream.concat(
                user.getAuthorities().stream(),
                scopes.stream().map(x -> "SCOPE_" + x).map(SimpleGrantedAuthority::new)
        ).toList());
        this.user = user;
        this.scopes = scopes;
        setAuthenticated(true);
    }

    public AuthenticatedUser(User user) {
        super(user.getAuthorities());
        this.user = user;
        this.scopes = Set.of();
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

}
