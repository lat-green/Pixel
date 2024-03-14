package com.example.pixel.server.authorization.security;

import com.example.pixel.server.authorization.dto.AuthenticatedUser;
import com.example.pixel.server.authorization.entity.User;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

@AllArgsConstructor
@Component
public class BearerTokenAuthenticationProvider implements AuthenticationProvider {

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        val token = ((BearerTokenAuthenticationToken) authentication).getToken();
        val authorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization != null) {
            final OAuth2AuthorizationRequest req = authorization.getAttribute(OAuth2AuthorizationRequest.class.getName());
            final Authentication principal = authorization.getAttribute(Principal.class.getName());
            val user = getUser(principal);
            val au = new AuthenticatedUser(user, req.getScopes());
            return au;
        }
        throw new BadCredentialsException("token = " + token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(BearerTokenAuthenticationToken.class);
    }

    private User getUser(Object object) {
        if (object instanceof User user)
            return user;
        if (object instanceof Authentication authentication)
            return getUser(authentication.getPrincipal());
        throw new UnsupportedOperationException("object = " + object);
    }

}
