package com.example.pixel.server.chat;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@AllArgsConstructor
public class HeaderVersionArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType() == ChatUser.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        var principal = webRequest.getUserPrincipal();
        return resolveArgument(principal);
    }

    private Object resolveArgument(Object principal) throws Exception {
        if (principal instanceof JwtAuthenticationToken token) {
            return resolveArgument(token.getPrincipal());
        }
        if (principal instanceof Jwt token) {
            var claims = token.getClaims();
            var userId = claims.get("user_id");
            if (userId instanceof Long id) {
                return userService.getOneUser(id);
            }
            if (userId instanceof String id) {
                try {
                    return userService.getOneUser(Long.parseLong(id));
                } catch (NumberFormatException e) {
                }
            }
        }
        return null;
    }

}
