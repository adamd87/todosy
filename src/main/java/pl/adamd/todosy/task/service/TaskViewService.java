package pl.adamd.todosy.task.service;

import pl.adamd.todosy.task.model.Task;

public interface TaskViewService {
    Task createNewTask(Long projectId, Task task);

    Task getTaskById(Long taskId);
}
