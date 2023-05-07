package pl.adamd.todosy.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adamd.todosy.project.controller.ProjectController;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.service.ProjectService;
import pl.adamd.todosy.task.controller.TaskController;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.model.TaskEntity;
import pl.adamd.todosy.task.model.mapper.TaskMapper;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class TaskViewServiceImpl implements TaskViewService {


    private final ProjectService projectService;

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public ResponseEntity<?> createNewTask(Long projectId, Task task) {

        try {
            Optional<ProjectEntity> projectEntity = projectService.getProject(projectId);
            Task taskResult = addTask(task, projectEntity.orElseThrow());
            getTaskResponse(taskResult);
            return ResponseEntity.ok()
                                 .body(taskResult);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Project not found with projectId " + projectId, HttpStatus.NOT_FOUND);
        }
    }

    private Task addTask(Task task, ProjectEntity projectEntity) {
        TaskEntity taskEntity = taskMapper.mapDtoToEntity(task);
        taskEntity.setStartDate(LocalDateTime.now());
        taskEntity.setProjectEntity(projectEntity);
        Task taskResult = taskMapper.mapEntityToDto(taskService.save(taskEntity));
        projectEntity.getTaskEntityList()
                     .add(taskEntity);
        projectService.save(projectEntity);
        return taskResult;
    }

    @Override
    public ResponseEntity<?> getTaskById(Long taskId) {
        try {
            Task task = taskMapper.mapEntityToDto(taskService.getTask(taskId)
                                                             .orElseThrow());
            getTaskResponse(task);
            return ResponseEntity.ok(task);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Task not found with taskId " + taskId, HttpStatus.NOT_FOUND);
        }


    }

    private void getTaskResponse(Task taskResult) {
        Link projectLink =
                linkTo(methodOn(ProjectController.class).getProjectById(taskResult.getProjectId())).withRel("project");
        Link taskSelf = linkTo(methodOn(TaskController.class).getTaskById(taskResult.getId())).withSelfRel();
        taskResult.add(projectLink);
        taskResult.add(taskSelf);
    }


}
