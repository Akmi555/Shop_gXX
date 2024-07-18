package de.ait_tr.gxx_shop.security.dto;
/*
@date 18.07.2024
@author Sergey Bugaienko
*/

import java.util.Objects;

public class RefreshRequestDto {
    private  String refreshToken;


    @Override
    public String toString() {
        return "Refresh request: refresh token: " + refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefreshRequestDto that = (RefreshRequestDto) o;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(refreshToken);
    }
}
