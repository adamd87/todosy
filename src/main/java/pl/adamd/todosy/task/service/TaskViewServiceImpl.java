package pl.adamd.todosy.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adamd.todosy.project.ProjectController;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.service.ProjectService;
import pl.adamd.todosy.task.TaskController;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.model.TaskEntity;
import pl.adamd.todosy.task.model.mapper.TaskMapper;

import java.time.LocalDateTime;
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
    public Task createNewTask(Long projectId, Task task) {

        Task taskResult = null;

        Optional<ProjectEntity> projectEntity = projectService.getProject(projectId);
        if (projectEntity.isPresent()) {
            TaskEntity taskEntity = taskMapper.mapDtoToEntity(task);
            taskEntity.setStartDate(LocalDateTime.now());
            taskEntity.setProjectEntity(projectEntity.get());
            taskResult = taskMapper.mapEntityToDto(taskService.save(taskEntity));
            projectEntity.get()
                         .getTaskEntityList()
                         .add(taskEntity);
            projectService.save(projectEntity.get());
            getTaskResponse(taskResult);
        }
        return taskResult;
    }

    @Override
    public Task getTaskById(Long taskId) {
        Task task = taskMapper.mapEntityToDto(taskService.getTask(taskId)
                                                         .orElseThrow());
        getTaskResponse(task);
        return task;
    }

    private void getTaskResponse(Task taskResult) {
        Link projectLink =
                linkTo(methodOn(ProjectController.class).getProjectById(taskResult.getProjectId())).withRel("project");
        Link taskSelf = linkTo(methodOn(TaskController.class).getTaskById(taskResult.getId())).withSelfRel();
        taskResult.add(projectLink);
        taskResult.add(taskSelf);
    }


}