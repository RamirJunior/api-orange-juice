package br.com.fcamara.apiorangejuice.api.controllers;

import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectResponse;
import br.com.fcamara.apiorangejuice.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @CacheEvict(value = "userProjectsCache", allEntries = true)
    public ResponseEntity<ProjectResponse> saveProject(@Valid @RequestBody ProjectRequest projectRequest) {
        var savedProject = projectService.saveProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> findProjects() {
        var projectList = projectService.findProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @GetMapping
    @Cacheable(value = "userProjectsCache")
    public ResponseEntity<List<ProjectResponse>> findUserProjects() {
        var userProjects = projectService.findUserProjects();
        return ResponseEntity.status(HttpStatus.OK).body(userProjects);
    }

    @PutMapping
    @CachePut(value = "userProjectsCache")
    public ResponseEntity<ProjectResponse> updateProject(@Valid @RequestBody ProjectRequest projectRequest) {
        var updatedProject = projectService.updateProject(projectRequest);
        return updatedProject.map(
                        projectResponse -> ResponseEntity.status(HttpStatus.OK).body(projectResponse))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @DeleteMapping("/{projectId}")
    @CachePut(value = "userProjectsCache")
    public ResponseEntity deleteProject(@PathVariable Long projectId) {
        var response = projectService.deleteProject(projectId);
        if (!response) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
