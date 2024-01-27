package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dto.ProjectRequest;
import br.com.fcamara.apiorangejuice.domain.entity.Project;
import br.com.fcamara.apiorangejuice.domain.entity.User;
import br.com.fcamara.apiorangejuice.repositories.ProjectRepository;
import br.com.fcamara.apiorangejuice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project saveProject(ProjectRequest projectRequest) {
        var project = convertToProject(projectRequest);
        return projectRepository.save(project);
    }

    public List<Project> findProjects() {
        return projectRepository.findByDeleted(false);
    }

    public List<Project> findUserProjects(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        var userProjectList = projectRepository.findByUser(user.get());
        return userProjectList.stream()
                .filter(project -> !project.isDeleted())
                .collect(Collectors.toList());
    }

    public boolean deleteProject(Long projectId) {
        Optional<Project> foundProject = projectRepository.findById(projectId);
        if (foundProject.isEmpty()) return false;

        var project = foundProject.get();
        project.setDeleted(true);
        return project.isDeleted();
    }

    private Project convertToProject(ProjectRequest request) {
        User user = getUserData(request);
        var tagList = getFormattedTags(request.getTags());
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setLink(request.getLink());
        project.setImageProject(request.getImageProject());
        project.setTags(tagList);
        project.setUser(user);
        return project;
    }

    private User getUserData(ProjectRequest request) {
        Optional<User> userFound = userRepository.findById(request.getUserId());
        return userFound.get();
    }

    private String getFormattedTags(List<String> tags) {
        if (tags.isEmpty()) return null;

        var tagList = tags.stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .toList()
                .toString();
        return tagList.substring(1, tagList.length() - 1);
    }
}
