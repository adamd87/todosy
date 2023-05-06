package pl.adamd.todosy.project.model.mapper;

import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.task.model.TaskEntity;
import pl.adamd.todosy.task.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapperImpl implements ProjectMapper {
    @Override
    public ProjectEntity mapDtoToEntity(Project project) {
        if (project != null) {
            List<TaskEntity> newTaskListEntity = new ArrayList<>();
            if (!project.getTaskList()
                        .isEmpty()) {
                for (Task task : project.getTaskList()) {

                }
            }
            ProjectEntity projectEntity = ProjectEntity.builder()
                                                       .name(project.getName())
                                                       .description(project.getDescription())
                                                       .startDate(project.getStartDate())
                                                       .deadline(project.getDeadline())
                                                       .resolveDate(project.getResolveDate())
                                                       .taskEntityList(newTaskListEntity)
                                                       .build();
            return projectEntity;

        }
        return null;
    }

    @Override
    public Project mapEntityToDto(ProjectEntity projectEntity) {
        return null;
    }
}
