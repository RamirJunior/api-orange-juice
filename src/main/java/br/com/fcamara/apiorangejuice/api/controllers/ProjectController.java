package br.com.fcamara.apiorangejuice.api.controllers;

import br.com.fcamara.apiorangejuice.api.dto.ProjectRequest;
import br.com.fcamara.apiorangejuice.domain.entity.Project;
import br.com.fcamara.apiorangejuice.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> saveProject(@Valid @RequestBody ProjectRequest projectRequest){
        var savedProject = projectService.saveProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping
    public ResponseEntity<List<Project>> findProjects(){
        var projectList = projectService.findProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Project>> findUserProjects(@PathVariable Long userId){
        var userProjects = projectService.findUserProjects(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userProjects);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteProject(@PathVariable Long projectId){
        var response = projectService.deleteProject(projectId);
        if (!response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
