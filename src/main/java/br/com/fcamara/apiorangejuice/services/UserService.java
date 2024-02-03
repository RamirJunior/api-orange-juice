package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dtos.user.UserRequest;
import br.com.fcamara.apiorangejuice.api.dtos.user.UserResponse;
import br.com.fcamara.apiorangejuice.api.utils.Constants;
import br.com.fcamara.apiorangejuice.api.utils.converters.UserDtoConverter;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import br.com.fcamara.apiorangejuice.exceptions.businessexceptions.UserAlreadyRegisteredException;
import br.com.fcamara.apiorangejuice.exceptions.businessexceptions.UserNotFoundException;
import br.com.fcamara.apiorangejuice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            throw new UserAlreadyRegisteredException(Constants.EMAIL_REGISTERED);

        userRequest.setPassword(encrypt(userRequest.getPassword()));
        var user = converter.toUser(userRequest);
        User savedUser = userRepository.save(user);
        return converter.toUserResponse(savedUser);
    }

    public Optional<User> findById(Long id) {
        var currentUser = userRepository.findById(id);
        var foundUser = userRepository.findById(currentUser.get().getId());
        if (foundUser.isEmpty())
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);

        return foundUser;
    }

    public UserDetails findByEmail(String login) {
        return userRepository.findByEmail(login);
    }

    private String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public User getCurrentUser() {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (currentAuth.isAuthenticated()) {
            var userDetails = (UserDetails) currentAuth.getPrincipal();
            currentUser = (User) userDetails;
        }
        return currentUser;
    }
}
