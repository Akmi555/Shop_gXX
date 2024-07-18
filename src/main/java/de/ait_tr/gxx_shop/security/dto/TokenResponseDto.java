package de.ait_tr.gxx_shop.security.dto;
/*
@date 18.07.2024
@author Sergey Bugaienko
*/

import java.util.Objects;

public class TokenResponseDto {
    private  String refreshToken;
    private  String accessToken;

    public TokenResponseDto() {
    }

    public TokenResponseDto(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }


    @Override
    public String toString() {
        return String.format("Token Response: access token: %s refresh token: %s", accessToken, refreshToken);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenResponseDto that = (TokenResponseDto) o;
        return Objects.equals(refreshToken, that.refreshToken) && Objects.equals(accessToken, that.accessToken);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(refreshToken);
        result = 31 * result + Objects.hashCode(accessToken);
        return result;
    }





}
