package br.com.fcamara.apiorangejuice.api.controllers;

import br.com.fcamara.apiorangejuice.api.dto.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dto.ProjectResponse;
import br.com.fcamara.apiorangejuice.domain.entity.Project;
import br.com.fcamara.apiorangejuice.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/{userId}")
    public ResponseEntity<Project> saveProject(@PathVariable Long userId,
                                               @Valid @RequestBody ProjectRequest projectRequest) {
        var savedProject = projectService.saveProject(userId, projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> findProjects() {
        var projectList = projectService.findProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Project>> findUserProjects(@PathVariable Long userId) {
        var userProjects = projectService.findUserProjects(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userProjects);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long userId,
                                                 @Valid @RequestBody Project project) {
        var updatedProject = projectService.updateProject(userId, project);
        if (updatedProject.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.status(HttpStatus.OK).body(updatedProject.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteProject(@PathVariable Long projectId, @PathVariable Long userId) {
        var response = projectService.deleteProject(userId, projectId);
        if (!response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
