package pl.adamd.todosy.task.model.mapper;

import org.springframework.stereotype.Component;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.model.TaskEntity;

@Component
public class TaskMapperImpl implements TaskMapper {

    public Task mapEntityToDto(TaskEntity taskEntity) {
        if (taskEntity != null) {
            return Task.builder()
                       .id(taskEntity.getId())
                       .name(taskEntity.getName())
                       .description(taskEntity.getDescription())
                       .startDate(taskEntity.getStartDate())
                       .deadline(taskEntity.getDeadline())
                       .resolveDate(taskEntity.getResolveDate())
                       .projectId(taskEntity.getProjectEntity()
                                            .getId())
                       .build();
        }
        return null;
    }

    @Override
    public TaskEntity mapDtoToEntity(Task task) {

        if (task != null) {
            return TaskEntity.builder()
                             .name(task.getName())
                             .description(task.getDescription())
                             .startDate(task.getStartDate())
                             .deadline(task.getDeadline())
                             .resolveDate(task.getResolveDate())
                             .build();
        }
        return null;
    }
}
