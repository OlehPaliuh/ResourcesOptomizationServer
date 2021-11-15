package com.resource.optimization.security.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class JwtResponseDto implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final Long id;
    private final String username;
    private final String accessToken;
    private final String refreshToken;

    public JwtResponseDto(String accessToken, String refreshToken, Long id, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
    }

}
