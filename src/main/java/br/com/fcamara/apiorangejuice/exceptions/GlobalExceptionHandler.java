package br.com.fcamara.apiorangejuice.exceptions;

import br.com.fcamara.apiorangejuice.api.dtos.login.FailLoginResponse;
import br.com.fcamara.apiorangejuice.exceptions.businessexceptions.ErrorField;
import br.com.fcamara.apiorangejuice.exceptions.businessexceptions.UnauthorizedProjectResourceException;
import br.com.fcamara.apiorangejuice.exceptions.businessexceptions.UserAlreadyRegisteredException;
import br.com.fcamara.apiorangejuice.exceptions.businessexceptions.UserNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorField> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException exception) {
        ErrorField error = new ErrorField(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorField> handleUserNotFoundException(UserAlreadyRegisteredException exception) {
        ErrorField error = new ErrorField(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UnauthorizedProjectResourceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorField> handleUnauthorizedProjectResourceException(
            UnauthorizedProjectResourceException exception) {
        ErrorField error = new ErrorField(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FailLoginResponse> handleAuthenticationException() {
        var failResponse = new FailLoginResponse(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorField> handleValidationException(ValidationException exception) {
        ErrorField error = new ErrorField(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
