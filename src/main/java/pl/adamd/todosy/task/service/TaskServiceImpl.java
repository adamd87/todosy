package pl.adamd.todosy.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamd.todosy.task.TaskRepository;
import pl.adamd.todosy.task.model.TaskEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    @Override
    public Optional<TaskEntity> getTask(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public TaskEntity save(TaskEntity task) {
        return taskRepository.save(task);
    }
}
