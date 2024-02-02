package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectResponse;
import br.com.fcamara.apiorangejuice.api.utils.Constants;
import br.com.fcamara.apiorangejuice.api.utils.converters.ProjectDtoConverter;
import br.com.fcamara.apiorangejuice.domain.entities.Project;
import br.com.fcamara.apiorangejuice.exceptions.businessexceptions.UnauthorizedProjectResourceException;
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

    public ProjectResponse saveProject(ProjectRequest projectRequest) {
        var currentUser = userService.getCurrentUserData();
        var project = projectConverter.toProject(currentUser.getId(), projectRequest);
        var savedProject = projectRepository.save(project);
        return projectConverter.toProjectResponse(savedProject);
    }

    public List<ProjectResponse> findProjects() {
        var projectList = projectRepository.findByDeleted(false);
        return projectConverter.toProjectResponseList(projectList);
    }

    public List<ProjectResponse> findUserProjects() {
        var currentUser = userService.getCurrentUserData();
        var allUserProjects = projectRepository.findByUser(currentUser);
        var projectList = allUserProjects.stream()
                .filter(project -> !project.isDeleted())
                .toList();
        return projectConverter.toProjectResponseList(projectList);
    }

    public Optional<ProjectResponse> updateProject(ProjectRequest projectRequest) {
        var currentUser = userService.getCurrentUserData();
        var project = projectConverter.toProject(currentUser.getId(), projectRequest);
        Optional<Project> foundProject = projectRepository.findById(project.getId());
        var toBeUpdatedproject = foundProject.get();

        if (!belongToUser(currentUser.getId(), toBeUpdatedproject.getUser().getId()))
            throw new UnauthorizedProjectResourceException(Constants.UNAUTHORIZED_PROJECT);

        BeanUtils.copyProperties(project, toBeUpdatedproject, "id");
        var updatedProject = projectRepository.save(toBeUpdatedproject);
        var projectResponse = projectConverter.toProjectResponse(updatedProject);
        return Optional.of(projectResponse);
    }

    public boolean deleteProject(Long projectId) {
        var current = userService.getCurrentUserData();
        Optional<Project> foundProject = projectRepository.findById(projectId);
        var toBeDeletedproject = foundProject.get();
        if (!belongToUser(current.getId(), toBeDeletedproject.getUser().getId()))
            throw new UnauthorizedProjectResourceException(Constants.UNAUTHORIZED_PROJECT);

        var project = foundProject.get();
        project.setDeleted(true);
        return project.isDeleted();
    }

    private boolean belongToUser(Long currentUserId, Long userIdProject) {
        return currentUserId == userIdProject;
    }
}
