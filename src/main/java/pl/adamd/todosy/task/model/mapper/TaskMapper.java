package pl.adamd.todosy.task.model.mapper;

import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.model.TaskEntity;


public interface TaskMapper {

    Task mapEntityToDto(TaskEntity taskEntity);
    TaskEntity mapDtoToEntity(Task task);
}
