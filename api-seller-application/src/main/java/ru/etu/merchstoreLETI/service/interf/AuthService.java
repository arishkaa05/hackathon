package ru.etu.merchstoreLETI.service.interf;

import ru.etu.merchstoreLETI.web.dto.auth.JWTRequest;
import ru.etu.merchstoreLETI.web.dto.auth.JWTResponse;

public interface AuthService {
    JWTResponse login(JWTRequest loginRequest);

    JWTResponse refresh(String refreshToken);

}
