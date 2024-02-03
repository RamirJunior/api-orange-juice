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

import static br.com.fcamara.apiorangejuice.api.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PROJECT_CONTROLLER_MAPPING_ROUTE)
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @CacheEvict(value = USER_PROJECTS_CACHE, allEntries = true)
    public ResponseEntity<ProjectResponse> saveProject(@Valid @RequestBody ProjectRequest projectRequest) {
        var savedProject = projectService.saveProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping(GET_ALL_PROJECTS_ROUTE)
    public ResponseEntity<List<ProjectResponse>> findAllProjects() {
        var projectList = projectService.findAllProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @GetMapping
    @Cacheable(value = USER_PROJECTS_CACHE)
    public ResponseEntity<List<ProjectResponse>> findUserProjects() {
        var userProjects = projectService.findUserProjects();
        return ResponseEntity.status(HttpStatus.OK).body(userProjects);
    }

    @PutMapping
    @CachePut(value = USER_PROJECTS_CACHE)
    public ResponseEntity<ProjectResponse> updateProject(@RequestBody ProjectRequest projectRequest) {
        var updatedProject = projectService.updateProject(projectRequest);
        return updatedProject.map(
                projectResponse -> ResponseEntity.status(HttpStatus.OK).body(projectResponse))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @DeleteMapping(DELETE_PROJECT_ROUTE)
    @CachePut(value = USER_PROJECTS_CACHE)
    public ResponseEntity deleteProject(@PathVariable Long projectId) {
        var response = projectService.deleteProject(projectId);
        if (!response) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
