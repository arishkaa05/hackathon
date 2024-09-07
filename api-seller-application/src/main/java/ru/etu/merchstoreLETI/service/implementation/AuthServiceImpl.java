package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.security.JwtTokenProvider;
import ru.etu.merchstoreLETI.service.interf.AuthService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.web.dto.auth.JWTRequest;
import ru.etu.merchstoreLETI.web.dto.auth.JWTResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public JWTResponse login(JWTRequest loginRequest) {
        JWTResponse jwtResponse = new JWTResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        User user = userService.getByEmail(loginRequest.getEmail());
        jwtResponse.setId(user.getId());
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setImageUrl(user.getImageUrl());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(),user.getEmail(),user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(),user.getEmail()));
        return jwtResponse;
    }

    @Override
    public JWTResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}

