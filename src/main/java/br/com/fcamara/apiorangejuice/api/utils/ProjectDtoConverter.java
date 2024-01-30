package br.com.fcamara.apiorangejuice.api.utils;

import br.com.fcamara.apiorangejuice.api.dtos.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dtos.ProjectResponse;
import br.com.fcamara.apiorangejuice.api.dtos.UserResponse;
import br.com.fcamara.apiorangejuice.domain.entities.Project;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import br.com.fcamara.apiorangejuice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        project.setCreatedAt(LocalDate.now());
        return project;
    }

    public ProjectResponse toProjectResponse(Project project) {
        ProjectResponse projectResponse = new ProjectResponse();
        UserResponse userResponse = userConverter.toUserResponse(project.getUser());

        projectResponse.setId(project.getId());
        projectResponse.setTitle(project.getTitle());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setImageProject(project.getImageProject());
        projectResponse.setTags(project.getTags());
        projectResponse.setUser(userResponse);
        projectResponse.setCreatedAt(project.getCreatedAt());
        return projectResponse;
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
