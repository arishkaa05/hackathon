package ru.etu.merchstoreLETI.web.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UserDto")
public class UserDto {
    @Schema(description = "User Id",example = "1")
    private Long id;
    @Schema(description = "User city",example = "Moscow")
    private String city;
    @Schema(description = "User image",example = "image1.png")
    private String imageUrl;
    @Schema(description = "User username",example = "Vlad")
    private String username;
    @Schema(description = "User lastname",example = "Semenov")
    private String lastname;
    @Schema(description = "User email",example = "ooovladislavchik@gmail.com")
    private String email;
    @Schema(description = "User contact number",example = "89999999999")
    private String contactNumber;
    @Schema(description = "User password",example = "1q2w3e")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Schema(description = "User password confirmation",example = "1q2w3e")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;
}

