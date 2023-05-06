package pl.adamd.todosy.project.model.mapper;

import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.model.Project;

public interface ProjectMapper {
    Project mapEntityToDto(ProjectEntity projectEntity);

    ProjectEntity mapDtoToEntitySaveNew(Project project);
}
