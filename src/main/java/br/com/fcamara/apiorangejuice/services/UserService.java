package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dtos.UserRequest;
import br.com.fcamara.apiorangejuice.api.dtos.UserResponse;
import br.com.fcamara.apiorangejuice.api.utils.UserDtoConverter;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import br.com.fcamara.apiorangejuice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter converter;

    public UserResponse saveUser(UserRequest userRequest) {
        var user = converter.toUser(userRequest);
        User savedUser = userRepository.save(user);
        return converter.toUserResponse(savedUser);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
