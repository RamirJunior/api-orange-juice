package br.com.fcamara.apiorangejuice.api.dtos.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUser {
    private Long id;
    private String username;
    private String email;
}
