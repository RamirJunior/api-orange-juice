package br.com.fcamara.apiorangejuice.exceptions.businessexceptions;

import org.springframework.validation.FieldError;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
