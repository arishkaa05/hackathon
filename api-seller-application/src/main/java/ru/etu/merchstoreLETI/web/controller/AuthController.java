package ru.etu.merchstoreLETI.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.etu.merchstoreLETI.service.interf.AuthService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.web.dto.MessageResponse;
import ru.etu.merchstoreLETI.web.dto.auth.JWTRequest;
import ru.etu.merchstoreLETI.web.dto.auth.JWTResponse;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication Controller",description = "Authentication API")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public JWTResponse login(@Validated @RequestBody JWTRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Validated @RequestBody UserDto userDto){
        userService.createEntity(userDto);
        return new ResponseEntity<>(new MessageResponse("Пользователь зарегистрирован!"), HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public JWTResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }

}

