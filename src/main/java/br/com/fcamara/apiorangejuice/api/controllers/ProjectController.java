package br.com.fcamara.apiorangejuice.api.controllers;

import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectResponse;
import br.com.fcamara.apiorangejuice.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "ProjectController", description = "Endpoints related to projects")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Save a new project for a user", description = "Endpoint to save a new project for a user. Request requires a Bearer Token. Restricted access for any role.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}")
    @CacheEvict(value = "userProjectsCache", allEntries = true)
    public ResponseEntity<ProjectResponse> saveProject(@PathVariable Long userId,
                                                       @Valid @RequestBody ProjectRequest projectRequest) {
        var savedProject = projectService.saveProject(userId, projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @Operation(summary = "Find all projects", description = "Endpoint to find all registered projects. Request requires a Bearer Token. Restricted access for any role.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> findProjects() {
        var projectList = projectService.findProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @Operation(summary = "Find all user projects by ID", description = "Endpoint to find all registered projects of a user. Request requires a Bearer Token. Restricted access for USER role.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    @Cacheable(value = "userProjectsCache", key = "#userId")
    public ResponseEntity<List<ProjectResponse>> findUserProjects(@PathVariable Long userId) {
        var userProjects = projectService.findUserProjects(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userProjects);
    }

    @Operation(summary = "Update an existing project", description = "Endpoint to update an existing project of a user. Request requires a Bearer Token. Restricted access for any role.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{userId}")
    @CachePut(value = "userProjectsCache")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long userId,
                                                         @Valid @RequestBody ProjectRequest projectRequest) {
        var updatedProject = projectService.updateProject(userId, projectRequest);
        return updatedProject.map(
                        projectResponse -> ResponseEntity.status(HttpStatus.OK).body(projectResponse))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @Operation(summary = "Delete a project", description = "Endpoint to delete a project of a user. Request requires a Bearer Token. Restricted access for any role.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{userId}/{projectId}")
    @CachePut(value = "userProjectsCache")
    public ResponseEntity deleteProject(@PathVariable Long userId, @PathVariable Long projectId) {
        var response = projectService.deleteProject(userId, projectId);
        if (!response) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
