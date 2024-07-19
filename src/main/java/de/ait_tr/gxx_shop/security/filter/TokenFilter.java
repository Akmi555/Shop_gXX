package de.ait_tr.gxx_shop.security.filter;
/*
@date 19.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.security.AuthInfo;
import de.ait_tr.gxx_shop.security.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // Преобразуем ServletRequest в HttpServletRequest для работы с заголовками
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String accessToken = getTokenFromRequest(httpServletRequest);

        if (accessToken != null && tokenService.validateAccessToken(accessToken)) {
            // Извлекаем информацию о пользователе из токена
            Claims claims = tokenService.getAccessClaims(accessToken);
            AuthInfo authInfo = tokenService.mapClaimsToAuthinfo(claims);

            // Устанавливаем пользователя как аутентифицированного
            authInfo.setAuthenticated(true);

            // Помещаем объект AuthInfo в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authInfo);

        }

        // Продолжаем цепочку фильтров
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        // Извлекаем заголовок Authorization
        String bearerToken = request.getHeader("Authorization");

        // Проверяем, что заголовок не пустой и начинается с "Bearer "
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Обрезаем префикс "Bearer " и возвращаем сам токен
            return bearerToken.substring(7);
        }

        // Если заголовок пустой или не начинается с "Bearer ", возвращаем null
        return null;

    }
}
