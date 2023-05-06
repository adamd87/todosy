package pl.adamd.todosy.task.service;

import pl.adamd.todosy.task.model.TaskEntity;

import java.util.Optional;

public interface TaskService {
    Optional<TaskEntity> getTask(Long id);

    TaskEntity save(TaskEntity task);
}
