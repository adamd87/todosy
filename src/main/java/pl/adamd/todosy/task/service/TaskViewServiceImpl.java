package pl.adamd.todosy.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
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
import pl.adamd.todosy.validation.ResponseManager;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class TaskViewServiceImpl implements TaskViewService {


    private final ProjectService projectService;

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final ResponseManager responseManager;

    private static final String PROJECT = "project";

    @Override
    @Transactional
    public ResponseEntity<?> createNewTask(Long projectId, Task task) {

        try {
            ProjectEntity projectEntity = projectService.getProject(projectId)
                                                        .orElseThrow();
            if (!projectEntity.isResolved()) {
                Task taskResult = addTask(task, projectEntity);
                getTaskResponse(taskResult);
                return ResponseEntity.ok(taskResult);
            }
            else {
                return responseManager.projectAlreadyResolvedResponseEntity(projectId);
            }
        } catch (NoSuchElementException e) {
            return responseManager.projectNotFoundResponseEntity(projectId);
        }
    }

    private Task addTask(Task task, ProjectEntity projectEntity) {
        TaskEntity taskEntity = taskMapper.mapDtoToEntity(task);
        taskEntity.setStartDate(OffsetDateTime.now());
        taskEntity.setProjectEntity(projectEntity);
        Task taskResult = taskMapper.mapEntityToDto(taskService.save(taskEntity));
        projectEntity.getTaskEntityList()
                     .add(taskEntity);
        projectEntity.setUpdateDate(OffsetDateTime.now());
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
            return responseManager.taskNotFoundResponseEntity(taskId);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> resolveTask(Long taskId) {
        try {
            TaskEntity taskEntity = taskService.getTask(taskId)
                                               .orElseThrow();
            if (taskEntity.isResolved()) {
                return responseManager.taskAlreadyResolvedResponseEntity(taskId);
            }
            taskEntity.setResolveDate(OffsetDateTime.now());
            taskEntity.setResolved(true);
            taskService.save(taskEntity);
            ProjectEntity projectEntity = projectService.getProject(taskEntity.getProjectEntity()
                                                                              .getId())
                                                        .orElseThrow();
            if (projectEntity.getTaskEntityList()
                             .stream()
                             .allMatch(TaskEntity::isResolved)) {
                projectEntity.setAllTasksDone(true);
            }
            projectEntity.setUpdateDate(OffsetDateTime.now());
            projectService.save(projectEntity);

            Task task = taskMapper.mapEntityToDto(taskEntity);
            getTaskResponse(task);
            return ResponseEntity.ok(task);
        } catch (NoSuchElementException e) {
            return responseManager.taskNotFoundResponseEntity(taskId);
        }
    }

    private void getTaskResponse(Task taskResult) {
        Link projectLink =
                linkTo(methodOn(ProjectController.class).getProjectById(taskResult.getProjectId())).withRel(PROJECT);
        Link taskSelf = linkTo(methodOn(TaskController.class).getTaskById(taskResult.getId())).withSelfRel();
        taskResult.add(projectLink);
        taskResult.add(taskSelf);
    }
}
