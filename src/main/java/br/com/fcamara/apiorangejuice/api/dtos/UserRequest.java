package br.com.fcamara.apiorangejuice.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "Name is required.")
    private String firstname;

    @NotBlank(message = "Lastname is required.")
    private String lastname;

    @Email
    @NotBlank(message = "Email is required.")
    private String email;
    private String password;
}
