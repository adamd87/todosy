package pl.adamd.todosy.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import pl.adamd.todosy.project.controller.ProjectController;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.model.mapper.ProjectMapper;
import pl.adamd.todosy.task.controller.TaskController;
import pl.adamd.todosy.task.model.TaskEntity;
import pl.adamd.todosy.task.service.TaskService;
import pl.adamd.todosy.validation.ResponseManager;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ProjectViewServiceImpl implements ProjectViewService {


    private final ProjectService projectService;

    private final ProjectMapper projectMapper;

    private final ResponseManager responseManager;

    private final TaskService taskService;

    private static final String TASKS = "tasks";

    @Override
    public ResponseEntity<?> getProjectDetail(Long projectId) {
        try {
            ProjectEntity projectEntity = projectService.getProject(projectId)
                                                        .orElseThrow();
            return getProjectResponseEntity(projectMapper.mapEntityToDto(projectService.save(projectEntity)),
                                            projectEntity);
        } catch (NoSuchElementException e) {
            return responseManager.projectNotFoundResponseEntity(projectId);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewProject(Project project) {
        try {
            ProjectEntity projectEntity = projectService.save(projectMapper.mapDtoToEntitySaveNew(project));
            return getProjectResponseEntity(projectMapper.mapEntityToDto(projectService.save(projectEntity)),
                                            projectEntity);
        } catch (HttpStatusCodeException e) {
            return responseManager.processFailedResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<?> closeProject(Long projectId) {
        try {
            ProjectEntity projectEntity = projectService.getProject(projectId)
                                                        .orElseThrow();
            projectEntity.setResolveDate(OffsetDateTime.now());
            projectEntity.setResolved(true);
            return getProjectResponseEntity(projectMapper.mapEntityToDto(projectService.save(projectEntity)),
                                            projectEntity);
        } catch (NoSuchElementException e) {
            return responseManager.projectNotFoundResponseEntity(projectId);
        }
    }

    @Override
    public ResponseEntity<?> deleteProjectById(Long projectId) {
        try {
            ProjectEntity projectEntity = projectService.getProject(projectId)
                                                        .orElseThrow();
            if (projectEntity.getTaskEntityList()
                             .stream()
                             .allMatch(TaskEntity::isResolved)) {
                projectEntity.getTaskEntityList()
                             .forEach(taskService::deleteTask);
                return ResponseEntity.ok(projectService.deleteProject(projectEntity));
            }
            else {
                List<Long> taskIds = new ArrayList<>();
                projectEntity.getTaskEntityList()
                             .forEach(taskEntity -> {
                                 if (!taskEntity.isResolved()) {
                                     taskIds.add(taskEntity.getId());
                                 }
                             });
                return responseManager.projectHasOpenTasksResponseEntity(taskIds);
            }
        } catch (NoSuchElementException e) {
            return responseManager.projectNotFoundResponseEntity(projectId);
        }
    }

    private ResponseEntity<Project> getProjectResponseEntity(Project project, ProjectEntity projectEntity) {
        getProjectResponse(projectEntity, project);
        return ResponseEntity.ok(project);
    }

    private static void getProjectResponse(ProjectEntity projectEntity, Project project) {

        Link thisProjectLink = linkTo(ProjectController.class).slash(projectEntity.getId())
                                                              .withSelfRel();
        List<Link> linkList = new ArrayList<>();
        linkList.add(thisProjectLink);
        for (TaskEntity task : projectEntity.getTaskEntityList()) {
            Link taskLink = linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withRel(TASKS);
            linkList.add(taskLink);
        }
        project.add(linkList);
    }

}
