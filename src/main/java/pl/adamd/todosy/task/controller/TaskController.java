package pl.adamd.todosy.task.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.service.TaskViewService;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    TaskViewService taskViewService;

    @PostMapping(value = "/project/{projectId}/add")
    public ResponseEntity<?> addTask(@PathVariable Long projectId, @Valid @RequestBody Task task){
        return ResponseEntity.ok(taskViewService.createNewTask(projectId, task));
    }

    @GetMapping(value = "/get/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskViewService.getTaskById(taskId));
    }

    @PostMapping(value = "/resolve/{taskId}")
    public ResponseEntity<?> resolveTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskViewService.resolveTask(taskId));
    }
}
