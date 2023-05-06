package pl.adamd.todosy.project.service;

import pl.adamd.todosy.project.model.ProjectEntity;

import java.util.Optional;

public interface ProjectService {
    Optional<ProjectEntity> getProject(Long id);
    ProjectEntity save(ProjectEntity project);
}
