package pl.adamd.todosy.project.model.mapper;

import org.springframework.stereotype.Component;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.model.ProjectEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Component
public class ProjectMapperImpl implements ProjectMapper {


    @Override
    public Project mapEntityToDto(ProjectEntity projectEntity) {
        return Project.builder()
                      .name(projectEntity.getName())
                      .description(projectEntity.getDescription())
                      .startDate(projectEntity.getStartDate())
                      .deadline(projectEntity.getDeadline())
                      .updateDate(projectEntity.getUpdateDate())
                      .resolveDate(projectEntity.getResolveDate())
                      .resolved(projectEntity.isResolved())
                      .allTasksDone(projectEntity.isAllTasksDone())
                      .build();
    }

    @Override
    public ProjectEntity mapDtoToEntitySaveNew(Project project) {
        return ProjectEntity.builder()
                            .name(project.getName())
                            .description(project.getDescription())
                            .startDate(OffsetDateTime.now())
                            .deadline(project.getDeadline())
                            .resolveDate(project.getResolveDate())
                            .updateDate(project.getUpdateDate())
                            .allTasksDone(project.isAllTasksDone())
                            .resolved(project.isResolved())
                            .taskEntityList(new ArrayList<>())
                            .build();
    }
}
