package pl.adamd.todosy.project.service;

import pl.adamd.todosy.project.model.Project;

public interface ProjectViewService {
    Project getProjectDetail(Long projectId);

    Project createNewProject(Project project);
}
