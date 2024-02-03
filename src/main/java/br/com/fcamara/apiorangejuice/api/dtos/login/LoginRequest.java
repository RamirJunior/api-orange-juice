package br.com.fcamara.apiorangejuice.api.dtos.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fcamara.apiorangejuice.api.utils.Constants.REQUIRED_EMAIL_MESSAGE;
import static br.com.fcamara.apiorangejuice.api.utils.Constants.REQUIRED_PASSWORD_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = REQUIRED_EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = REQUIRED_PASSWORD_MESSAGE)
    private String password;
}
