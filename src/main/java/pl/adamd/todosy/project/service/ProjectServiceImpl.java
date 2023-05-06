package pl.adamd.todosy.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamd.todosy.project.ProjectRepository;
import pl.adamd.todosy.project.model.ProjectEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;

    @Override
    public Optional<ProjectEntity> getProject(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public void save(ProjectEntity project) {
        projectRepository.save(project);
    }
}
