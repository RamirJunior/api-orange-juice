package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dto.UserRequest;
import br.com.fcamara.apiorangejuice.api.dto.UserResponse;
import br.com.fcamara.apiorangejuice.domain.entity.User;
import br.com.fcamara.apiorangejuice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findAll() {
        var userList = userRepository.findAll();
        return userList.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public UserResponse saveUser(UserRequest userRequest) {
        var user = convertToUser(userRequest);
        User savedUser = userRepository.save(user);
        return convertToUserResponse(savedUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private User convertToUser(UserRequest request) {
        User user = new User();
        user.setFirstname(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        return response;
    }
}
