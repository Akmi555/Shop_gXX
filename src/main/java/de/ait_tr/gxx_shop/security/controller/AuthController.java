package de.ait_tr.gxx_shop.security.controller;

import de.ait_tr.gxx_shop.security.dto.LoginRequestDTO;
import de.ait_tr.gxx_shop.security.dto.RefreshRequestDto;
import de.ait_tr.gxx_shop.security.dto.TokenResponseDto;
import de.ait_tr.gxx_shop.security.service.AuthService;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@date 19.07.2024
@author Sergey Bugaienko
*/

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDTO loginRequestDTO){
        try {
            return authService.login(loginRequestDTO);
        } catch (AuthException e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/refresh")
    public TokenResponseDto refreshAccessToken(@RequestBody RefreshRequestDto refreshRequestDto) {
        try {
            return authService.refreshAccessToken(refreshRequestDto.getRefreshToken());
        } catch (AuthException e) {
            throw new RuntimeException();
        }
    }
}
