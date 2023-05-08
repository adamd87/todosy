package pl.adamd.todosy.project.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.repository.ProjectRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Optional<ProjectEntity> getProject(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public ProjectEntity save(ProjectEntity project) {
        return projectRepository.save(project);
    }

    @Override
    public boolean deleteProject(ProjectEntity projectEntity) {
        projectRepository.delete(projectEntity);
        return true;
    }
}
