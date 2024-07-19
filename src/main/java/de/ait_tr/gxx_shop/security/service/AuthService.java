package de.ait_tr.gxx_shop.security.service;
/*
@date 19.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.User;
import de.ait_tr.gxx_shop.security.dto.LoginRequestDTO;
import de.ait_tr.gxx_shop.security.dto.TokenResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private Map<String, String> refreshStorage;


    public AuthService(TokenService tokenService, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.refreshStorage = new HashMap<>();
    }

    public TokenResponseDto login(LoginRequestDTO loginRequestDTO) throws AuthException {
        String username = loginRequestDTO.username();
        UserDetails foundUser = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(loginRequestDTO.password(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(username, refreshToken);

            return new TokenResponseDto(accessToken, refreshToken);
        }
        throw new AuthException("Incorrect login and / or password");
    }

    public TokenResponseDto refreshAccessToken(String refreshToken) throws AuthException {
        boolean isValid = tokenService.validateRefreshToken(refreshToken);

        Claims refreshClaims = tokenService.getRefreshClaims(refreshToken);
        String username = refreshClaims.getSubject();

        String savedToken = refreshStorage.get(username);
        boolean isSaved = (savedToken != null && savedToken.equals(refreshToken));

        if (isValid && isSaved) {
            UserDetails foundUser = userDetailsService.loadUserByUsername(username);
            String accessToken = tokenService.generateAccessToken(foundUser);

            return new TokenResponseDto(accessToken, refreshToken);
        }
        throw new AuthException("Incorrect refresh token. Re login please");
    }
}
