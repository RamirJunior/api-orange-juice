package br.com.fcamara.apiorangejuice.api.utils.converters;

import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectOwner;
import br.com.fcamara.apiorangejuice.api.dtos.user.UserRequest;
import br.com.fcamara.apiorangejuice.api.dtos.user.UserResponse;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import br.com.fcamara.apiorangejuice.domain.entities.UserRole;
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
        user.setProfileImageAddress(request.getProfileImageUser());
        user.setRole(UserRole.USER);
        return user;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setProfileImageUser(user.getProfileImageAddress());
        return response;
    }

    public ProjectOwner toProjectOwner(User user) {
        ProjectOwner response = new ProjectOwner();
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setProfileImageUser(user.getProfileImageAddress());
        return response;
    }
}
