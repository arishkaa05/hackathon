package ru.etu.merchstoreLETI.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JWTRequest {
    @Schema(description = "email",example = "ooovladislavchik@gmail.com")
    @NotNull(message = "Username must be not null")
    private String email;
    @Schema(description = "password for request",example = "1q2w3e")
    @NotNull(message = "Password must be not null")
    private String password;
}

