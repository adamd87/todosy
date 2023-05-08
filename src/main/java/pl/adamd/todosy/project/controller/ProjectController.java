package pl.adamd.todosy.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.service.ProjectViewService;


@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    ProjectViewService projectViewService;

    @GetMapping(value = "/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectViewService.getProjectDetail(projectId));
    }

    @PostMapping
    private ResponseEntity<?> addProject(@Valid @RequestBody Project project) {
        return ResponseEntity.ok(projectViewService.createNewProject(project));
    }

    @PostMapping(value = "/close/{projectId}")
    private ResponseEntity<?> closeProject(@PathVariable Long projectId){
        return ResponseEntity.ok(projectViewService.closeProject(projectId));
    }

    @DeleteMapping(value = "/{projectId}")
    private ResponseEntity<?> deleteProject(@PathVariable Long projectId){
        return ResponseEntity.ok(projectViewService.deleteProjectById(projectId));
    }

}
