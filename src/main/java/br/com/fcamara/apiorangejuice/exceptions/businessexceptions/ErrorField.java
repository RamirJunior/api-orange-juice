package br.com.fcamara.apiorangejuice.exceptions.businessexceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorField {
    private String message;
}
