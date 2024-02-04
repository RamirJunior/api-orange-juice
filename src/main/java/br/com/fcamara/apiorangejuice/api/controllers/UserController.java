package br.com.fcamara.apiorangejuice.api.controllers;

import br.com.fcamara.apiorangejuice.api.dtos.login.LoginRequest;
import br.com.fcamara.apiorangejuice.api.dtos.login.SuccessLoginResponse;
import br.com.fcamara.apiorangejuice.api.dtos.user.UserRequest;
import br.com.fcamara.apiorangejuice.api.dtos.user.UserResponse;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import br.com.fcamara.apiorangejuice.services.TokenService;
import br.com.fcamara.apiorangejuice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.fcamara.apiorangejuice.api.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_CONTROLLER_MAPPING_ROUTE)
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(POST_USER_REGISTER_ROUTE)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        UserResponse savedUser = userService.register(userRequest);
        if (savedUser == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping(POST_USER_LOGIN_ROUTE)
    public ResponseEntity<SuccessLoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        var usernamePassword = tokenService.getAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.createToken((User) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
