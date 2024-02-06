package br.com.fcamara.apiorangejuice.api.dtos.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailLoginResponse {
    private boolean success;
}
