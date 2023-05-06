package pl.adamd.todosy.task.model.mapper;

import pl.adamd.todosy.task.model.TaskEntity;
import pl.adamd.todosy.task.model.Task;

public interface TaskMapper {
    TaskEntity mapDtoToEntity(Task task);
    Task mapEntityToDto(TaskEntity taskEntity);
}
