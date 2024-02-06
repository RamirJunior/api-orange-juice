package br.com.fcamara.apiorangejuice.api.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static br.com.fcamara.apiorangejuice.api.utils.Constants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = REQUIRED_FIRSTNAME_MESSAGE)
    private String firstname;

    @NotBlank(message = REQUIRED_LASTNAME_MESSAGE)
    private String lastname;

    @Email
    @NotBlank(message = REQUIRED_EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = REQUIRED_PASSWORD_MESSAGE)
    @Length(min = 6, message = MIN_SIZE_PASSWORD_MESSAGE)
    private String password;
    private String profileImageUser;
}
