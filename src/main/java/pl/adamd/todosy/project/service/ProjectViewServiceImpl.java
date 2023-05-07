package pl.adamd.todosy.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import pl.adamd.todosy.project.controller.ProjectController;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.model.mapper.ProjectMapper;
import pl.adamd.todosy.task.controller.TaskController;
import pl.adamd.todosy.task.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ProjectViewServiceImpl implements ProjectViewService {


    private final ProjectService projectService;

    private final ProjectMapper projectMapper;

    @Override
    public ResponseEntity<?> getProjectDetail(Long projectId) {
        try {
            Optional<ProjectEntity> projectEntity = projectService.getProject(projectId);
            Project project = projectEntity.map(projectMapper::mapEntityToDto)
                                           .orElseThrow();
            getProjectResponse(projectEntity.get(), project);
            return ResponseEntity.ok(project);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Project not found with projectId " + projectId, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewProject(Project project) {
        try {
            ProjectEntity projectEntity = projectService.save(projectMapper.mapDtoToEntitySaveNew(project));
            project = projectMapper.mapEntityToDto(projectEntity);
            getProjectResponse(projectEntity, project);
            return ResponseEntity.ok(project);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>("Save Project process fail: " + e.getMessage(), e.getStatusCode());
        }
    }

    private static void getProjectResponse(ProjectEntity projectEntity, Project project) {

        Link thisProjectLink = linkTo(ProjectController.class).slash(projectEntity.getId())
                                                              .withSelfRel();
        List<Link> linkList = new ArrayList<>();
        linkList.add(thisProjectLink);
        for (TaskEntity task : projectEntity.getTaskEntityList()) {
            Link taskLink = linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withRel("tasks");
            linkList.add(taskLink);
        }
        project.add(linkList);
    }


}
