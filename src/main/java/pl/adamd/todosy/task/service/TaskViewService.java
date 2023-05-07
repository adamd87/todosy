package pl.adamd.todosy.task.service;

import org.springframework.http.ResponseEntity;
import pl.adamd.todosy.task.model.Task;

public interface TaskViewService {
    ResponseEntity<?> createNewTask(Long projectId, Task task);

    ResponseEntity<?> getTaskById(Long taskId);

    ResponseEntity<?> resolveTask(Long taskId);
}
