package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dto.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dto.ProjectResponse;
import br.com.fcamara.apiorangejuice.api.utils.ProjectDtoConverter;
import br.com.fcamara.apiorangejuice.domain.entity.Project;
import br.com.fcamara.apiorangejuice.domain.entity.User;
import br.com.fcamara.apiorangejuice.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectDtoConverter projectConverter;

    public Project saveProject(Long userId, ProjectRequest projectRequest) {
        var project = projectConverter.toProject(userId, projectRequest);
        return projectRepository.save(project);
    }

    public List<ProjectResponse> findProjects() {
        var projectList = projectRepository.findByDeleted(false);
        return projectConverter.toProjectResponseList(projectList);
    }

    public List<Project> findUserProjects(Long userId) {
        Optional<User> user = userService.findById(userId);
        var userProjectList = projectRepository.findByUser(user.get());
        return userProjectList.stream()
                .filter(project -> !project.isDeleted())
                .collect(Collectors.toList());
    }

    public Optional<Project> updateProject(Long userId, Project project) {
        Optional<Project> toBeUpdated = projectRepository.findById(project.getId());
        if (toBeUpdated.isEmpty())
            return toBeUpdated;

        if (belongToUser(userId, toBeUpdated.get()))
            BeanUtils.copyProperties(toBeUpdated, project, "id");

        var updatedproject = projectRepository.save(project);
        return Optional.of(updatedproject);
    }

    public boolean deleteProject(Long userId, Long projectId) {
        Optional<Project> foundProject = projectRepository.findById(projectId);
        if (!belongToUser(userId, foundProject.get())) return false;

        var project = foundProject.get();
        project.setDeleted(true);
        return project.isDeleted();
    }

    private boolean belongToUser(Long userId, Project project) {
        return userId == project.getUser().getId();
    }
}
