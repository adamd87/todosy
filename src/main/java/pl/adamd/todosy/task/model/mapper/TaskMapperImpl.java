package pl.adamd.todosy.task.model.mapper;

import org.springframework.stereotype.Component;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.model.TaskEntity;

@Component
public class TaskMapperImpl implements TaskMapper {

    public Task mapEntityToDto(TaskEntity taskEntity) {
        return Task.builder()
                   .id(taskEntity.getId())
                   .name(taskEntity.getName())
                   .description(taskEntity.getDescription())
                   .startDate(taskEntity.getStartDate())
                   .deadline(taskEntity.getDeadline())
                   .resolveDate(taskEntity.getResolveDate())
                   .resolved(taskEntity.isResolved())
                   .projectId(taskEntity.getProjectEntity()
                                        .getId())
                   .build();
    }

    @Override
    public TaskEntity mapDtoToEntity(Task task) {
        return TaskEntity.builder()
                         .name(task.getName())
                         .description(task.getDescription())
                         .startDate(task.getStartDate())
                         .deadline(task.getDeadline())
                         .resolveDate(task.getResolveDate())
                         .resolved(task.isResolved())
                         .build();
    }
}
