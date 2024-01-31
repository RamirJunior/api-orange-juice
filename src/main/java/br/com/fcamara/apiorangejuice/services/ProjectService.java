package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectResponse;
import br.com.fcamara.apiorangejuice.api.utils.ProjectDtoConverter;
import br.com.fcamara.apiorangejuice.domain.entities.Project;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import br.com.fcamara.apiorangejuice.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectDtoConverter projectConverter;

    public ProjectResponse saveProject(Long userId, ProjectRequest projectRequest) {
        var project = projectConverter.toProject(userId, projectRequest);
        var savedProject = projectRepository.save(project);
        return projectConverter.toProjectResponse(savedProject);
    }

    public List<ProjectResponse> findProjects() {
        var projectList = projectRepository.findByDeleted(false);
        return projectConverter.toProjectResponseList(projectList);
    }

    public List<ProjectResponse> findUserProjects(Long userId) {
        Optional<User> user = userService.findById(userId);
        var allUserProjects = projectRepository.findByUser(user.get());
        var projectList = allUserProjects.stream()
                .filter(project -> !project.isDeleted())
                .toList();
        return projectConverter.toProjectResponseList(projectList);
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
