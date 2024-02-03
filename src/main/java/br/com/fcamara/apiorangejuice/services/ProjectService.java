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

    private final ProjectDtoConverter projectConverter;
    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectResponse saveProject(ProjectRequest projectRequest) {
        var user = userService.getCurrentUser();
        var project = projectConverter.toProject(user.getId(), projectRequest);
        var savedProject = projectRepository.save(project);
        return projectConverter.toProjectResponse(savedProject);
    }

    public List<ProjectResponse> findAllProjects() {
        var projectList = projectRepository.findByDeleted(false);
        return projectConverter.toProjectResponseList(projectList);
    }

    public List<ProjectResponse> findUserProjects() {
        var user = userService.getCurrentUser();
        var allUserProjects = projectRepository.findByUser(user);
        var projectList = allUserProjects.stream()
                .filter(project -> !project.isDeleted())
                .toList();
        return projectConverter.toProjectResponseList(projectList);
    }

    public Optional<ProjectResponse> updateProject(ProjectRequest projectRequest) {
        var user = userService.getCurrentUser();
        var project = projectConverter.toProject(user.getId(), projectRequest);
        Optional<Project> foundProject = projectRepository.findById(project.getId());
        var toBeUpdatedproject = foundProject.get();

        if (!belongToUser(user.getId(), toBeUpdatedproject.getUser().getId()))
            throw new UnauthorizedProjectResourceException(Constants.UNAUTHORIZED_PROJECT);

        BeanUtils.copyProperties(project, toBeUpdatedproject, "id");
        var updatedProject = projectRepository.save(toBeUpdatedproject);
        var projectResponse = projectConverter.toProjectResponse(updatedProject);
        return Optional.of(projectResponse);
    }

    public boolean deleteProject(Long projectId) {
        var user = userService.getCurrentUser();
        Optional<Project> foundProject = projectRepository.findById(projectId);
        var toBeDeletedproject = foundProject.get();
        if (!belongToUser(user.getId(), toBeDeletedproject.getUser().getId()))
            throw new UnauthorizedProjectResourceException(Constants.UNAUTHORIZED_PROJECT);

        var project = foundProject.get();
        project.setDeleted(true);
        return project.isDeleted();
    }

    private static boolean belongToUser(Long userIdReceived, Long userIdProject) {
        return userIdReceived == userIdProject;
    }
}
