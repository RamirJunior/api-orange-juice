package br.com.fcamara.apiorangejuice.exceptions.businessexceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
