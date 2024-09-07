package ru.etu.merchstoreLETI.web.dto.auth;

import lombok.Data;

@Data
public class JWTResponse {
    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String username;
    private String imageUrl;
}

