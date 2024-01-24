package br.com.fcamara.apiorangejuice.controllers;

import br.com.fcamara.apiorangejuice.domain.dto.UserRequest;
import br.com.fcamara.apiorangejuice.domain.entity.User;
import br.com.fcamara.apiorangejuice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserRequest userRequest) {
        User savedUser = userService.saveUser(userRequest);
        if (savedUser == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        var userList = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
}
