package pl.adamd.todosy.project.model.mapper;

import org.springframework.stereotype.Component;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.model.ProjectEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class ProjectMapperImpl implements ProjectMapper {


    @Override
    public Project mapEntityToDto(ProjectEntity projectEntity) {
        if (projectEntity != null) {
            return Project.builder()
                          .name(projectEntity.getName())
                          .description(projectEntity.getDescription())
                          .startDate(projectEntity.getStartDate())
                          .deadline(projectEntity.getDeadline())
                          .resolveDate(projectEntity.getResolveDate())
                          .build();
        }
        return null;
    }

    @Override
    public ProjectEntity mapDtoToEntitySaveNew(Project project) {
        if (project != null) {
            return ProjectEntity.builder()
                                .name(project.getName())
                                .description(project.getDescription())
                                .startDate(LocalDateTime.now())
                                .deadline(project.getDeadline())
                                .resolveDate(project.getResolveDate())
                                .taskEntityList(new ArrayList<>())
                                .build();
        }
        return null;
    }
}
