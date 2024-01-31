package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dtos.user.UserRequest;
import br.com.fcamara.apiorangejuice.api.dtos.user.UserResponse;
import br.com.fcamara.apiorangejuice.api.utils.UserDtoConverter;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import br.com.fcamara.apiorangejuice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter converter;

    public UserResponse register(UserRequest userRequest) {
        var existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null)
            throw new DataIntegrityViolationException("Email already registered.");

        userRequest.setPassword(encrypt(userRequest.getPassword()));
        var user = converter.toUser(userRequest);
        User savedUser = userRepository.save(user);
        return converter.toUserResponse(savedUser);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public UserDetails findByEmail(String login) {
        return userRepository.findByEmail(login);
    }

    private String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
