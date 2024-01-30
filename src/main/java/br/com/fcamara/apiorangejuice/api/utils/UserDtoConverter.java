package br.com.fcamara.apiorangejuice.api.utils;

import br.com.fcamara.apiorangejuice.api.dtos.UserRequest;
import br.com.fcamara.apiorangejuice.api.dtos.UserResponse;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    public User toUser(UserRequest request) {
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        return response;
    }
}
