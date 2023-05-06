package pl.adamd.todosy.project;

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

    @GetMapping(value = "/{projectId}", produces = {"application/hal+json"})
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectViewService.getProjectDetail(projectId));
    }

    @PostMapping
    private ResponseEntity<Project> addProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectViewService.createNewProject(project));
    }

}
