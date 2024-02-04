package br.com.fcamara.apiorangejuice.api.dtos.login;

import br.com.fcamara.apiorangejuice.api.dtos.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessLoginResponse {
    private boolean success;
    private String token;
    private UserResponse user;
}
