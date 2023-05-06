package pl.adamd.todosy.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adamd.todosy.project.ProjectController;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.model.mapper.ProjectMapper;
import pl.adamd.todosy.task.TaskController;
import pl.adamd.todosy.task.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ProjectViewServiceImpl implements ProjectViewService {


    private final ProjectService projectService;

    private final ProjectMapper projectMapper;

    @Override
    public Project getProjectDetail(Long projectId) {
        Optional<ProjectEntity> projectEntity = projectService.getProject(projectId);
        Project project = projectEntity.map(projectMapper::mapEntityToDto)
                                       .orElse(null);

        return getProjectResponse(projectEntity.orElseThrow(), project);
    }

    @Override
    @Transactional
    public Project createNewProject(Project project) {
        ProjectEntity projectEntity = projectService.save(projectMapper.mapDtoToEntitySaveNew(project));

        project = projectMapper.mapEntityToDto(projectEntity);

        return getProjectResponse(projectEntity, project);
    }

    private static Project getProjectResponse(ProjectEntity projectEntity, Project project) {

        Link thisProjectLink = linkTo(ProjectController.class).slash(projectEntity.getId())
                                                              .withSelfRel();
        List<Link> linkList = new ArrayList<>();
        linkList.add(thisProjectLink);
        for (TaskEntity task : projectEntity.getTaskEntityList()) {
            Link taskLink = linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withRel("tasks");
            linkList.add(taskLink);
        }
        return project.add(linkList);
    }


}
