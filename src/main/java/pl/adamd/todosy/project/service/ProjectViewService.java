package pl.adamd.todosy.project.service;

import org.springframework.http.ResponseEntity;
import pl.adamd.todosy.project.model.Project;

public interface ProjectViewService {
    ResponseEntity<?> getProjectDetail(Long projectId);

    ResponseEntity<?> createNewProject(Project project);

    ResponseEntity<?> closeProject(Long projectId);
}
