package br.com.fcamara.apiorangejuice.api.utils;

import br.com.fcamara.apiorangejuice.api.dto.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dto.ProjectResponse;
import br.com.fcamara.apiorangejuice.api.dto.UserResponse;
import br.com.fcamara.apiorangejuice.domain.entity.Project;
import br.com.fcamara.apiorangejuice.domain.entity.User;
import br.com.fcamara.apiorangejuice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectDtoConverter {

    private final UserService userService;
    private final UserDtoConverter userConverter;

    public Project toProject(Long userId, ProjectRequest request) {
        Optional<User> user = userService.findById(userId);
        var tagList = getStringTags(request.getTags());

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setLink(request.getLink());
        project.setImageProject(request.getImageProject());
        project.setTags(tagList);
        project.setUser(user.get());
        return project;
    }

    public ProjectResponse toProjectResponse(Project project) {
        ProjectResponse publicProject = new ProjectResponse();
        UserResponse userResponse = userConverter.toUserResponse(project.getUser());

        publicProject.setId(project.getId());
        publicProject.setTitle(project.getTitle());
        publicProject.setDescription(project.getDescription());
        publicProject.setImageProject(project.getImageProject());
        publicProject.setTags(project.getTags());
        publicProject.setUser(userResponse);
        return publicProject;
    }

    public List<ProjectResponse> toProjectResponseList(List<Project> projectList) {
        return projectList.stream()
                .map(this::toProjectResponse)
                .collect(Collectors.toList());
    }

    private String getStringTags(List<String> tags) {
        if (tags.isEmpty()) return null;

        var tagList = tags.stream()
                .map(String::trim)
                .toList()
                .toString();
        return tagList.substring(1, tagList.length() - 1);
    }
}
