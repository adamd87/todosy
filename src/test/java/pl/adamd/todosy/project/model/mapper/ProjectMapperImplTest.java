package pl.adamd.todosy.project.model.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.model.ProjectEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class ProjectMapperImplTest {


    public ProjectMapper getProjectMapper() {
        return new ProjectMapperImpl();
    }

    Project project = Project.builder()
                             .name("Project 1")
                             .description("description dto")
                             .deadline(LocalDate.of(2023, 5, 29))
                             .resolveDate(OffsetDateTime.of(LocalDateTime.of(2023, 5, 29, 12, 0, 0), ZoneOffset.UTC))
                             .build();

    ProjectEntity projectEntity = ProjectEntity.builder()
                                               .name("Project Entity")
                                               .description("description")
                                               .deadline(LocalDate.of(2025, 6, 14))
                                               .resolveDate(OffsetDateTime.of(LocalDateTime.of(2024, 7, 27, 11, 0, 0),
                                                                              ZoneOffset.UTC))
                                               .build();


    @Test
    void mapEntityToDto() {
        Project projectResult = getProjectMapper().mapEntityToDto(projectEntity);

        Assertions.assertEquals("Project Entity", projectResult.getName());
        Assertions.assertEquals("description", projectResult.getDescription());
        Assertions.assertNull(projectResult.getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 6, 14), projectResult.getDeadline());
        Assertions.assertEquals(OffsetDateTime.of(LocalDateTime.of(2024, 7, 27, 11, 0, 0), ZoneOffset.UTC),
                                projectResult.getResolveDate());
    }

    @Test
    void mapDtoToEntitySaveNew() {
        ProjectEntity projectEntityResult = getProjectMapper().mapDtoToEntitySaveNew(project);

        Assertions.assertEquals("Project 1", projectEntityResult.getName());
        Assertions.assertEquals("description dto", projectEntityResult.getDescription());
        Assertions.assertEquals(projectEntityResult.getStartDate()
                                                   .toLocalDate(), LocalDateTime.now()
                                                                                .toLocalDate());
        Assertions.assertEquals(LocalDate.of(2023, 5, 29), projectEntityResult.getDeadline());
        Assertions.assertEquals(OffsetDateTime.of(LocalDateTime.of(2023, 5, 29, 12, 0, 0), ZoneOffset.UTC), projectEntityResult.getResolveDate());
    }
}