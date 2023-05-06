package pl.adamd.todosy.task.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.adamd.todosy.project.ProjectRepository;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.task.model.TaskEntity;
import pl.adamd.todosy.task.model.Task;

@Component
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {
    private final ProjectRepository projectRepository;
    @Override
    public TaskEntity mapDtoToEntity(Task task) {
        if (task != null) {
            if (task.getProjectId()!= null){
                ProjectEntity projectEntity = projectRepository.getReferenceById(task.getProjectId());
                return TaskEntity.builder()
                                 .name(task.getName())
                                 .description(task.getDescription())
                                 .startDate(task.getStartDate())
                                 .deadline(task.getDeadline())
                                 .resolveDate(task.getResolveDate())
                                 .projectEntity(projectEntity)
                                 .build();
            }
        }
        return null;
    }

    @Override
    public Task mapEntityToDto(TaskEntity taskEntity) {
        return null;
    }
}
