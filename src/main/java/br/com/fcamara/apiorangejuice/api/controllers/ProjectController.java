package br.com.fcamara.apiorangejuice.api.controllers;

import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectRequest;
import br.com.fcamara.apiorangejuice.api.dtos.project.ProjectResponse;
import br.com.fcamara.apiorangejuice.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.fcamara.apiorangejuice.api.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PROJECT_CONTROLLER_MAPPING_ROUTE)
@Tag(name = "ProjectController", description = "Endpoints relacionados aos projetos")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Salvar um novo projeto para um usuário", description = "Recurso para salvar um novo projeto para um usuário. Requisição exige um Bearer Token. Acesso restrito para qualquer papel.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ProjectResponse> saveProject(@Valid @RequestBody ProjectRequest projectRequest) {
        var savedProject = projectService.saveProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @Operation(summary = "Buscar todos os projetos", description = "Recurso para buscar todos os projetos cadastrados. Requisição exige um Bearer Token. Acesso restrito para qualquer papel.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping(GET_ALL_PROJECTS_ROUTE)
    public ResponseEntity<List<ProjectResponse>> findAllProjects() {
        var projectList = projectService.findAllProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @Operation(summary = "Buscar todos os projetos do usuário por id", description = "Recurso para fazer a busca de todos os projetos cadastrados do usuário. Requisição exige um Bearer Token. Acesso restrito para USER",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> findUserProjects() {
        var userProjects = projectService.findUserProjects();
        return ResponseEntity.status(HttpStatus.OK).body(userProjects);
    }

    @Operation(summary = "Atualizar um projeto existente", description = "Recurso para atualizar um projeto existente de um usuário. Requisição exige um Bearer Token. Acesso restrito para qualquer papel.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<ProjectResponse> updateProject(@RequestBody ProjectRequest projectRequest) {
        var updatedProject = projectService.updateProject(projectRequest);
        return updatedProject.map(
                        projectResponse -> ResponseEntity.status(HttpStatus.OK).body(projectResponse))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @Operation(summary = "Excluir um projeto", description = "Recurso para excluir um projeto de um usuário. Requisição exige um Bearer Token. Acesso restrito para qualquer papel.",
            security = @SecurityRequirement(name = "security")
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(DELETE_PROJECT_ROUTE)
    public ResponseEntity deleteProject(@PathVariable Long projectId) {
        var response = projectService.deleteProject(projectId);
        if (!response) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
