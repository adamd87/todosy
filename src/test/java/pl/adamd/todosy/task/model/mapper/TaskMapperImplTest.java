package pl.adamd.todosy.task.model.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.model.TaskEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

class TaskMapperImplTest {

    public TaskMapper getTaskMapper() {
        return new TaskMapperImpl();
    }

    ProjectEntity projectEntity = ProjectEntity.builder().id(2L)
                                               .build();

    Task taskDto = Task.builder()
                       .name("Task DTO")
                       .description("Description DTO")
                       .startDate(LocalDateTime.now())
                       .deadline(LocalDate.of(2023, 12, 4))
                       .resolveDate(LocalDateTime.of(2022, 11, 4, 12, 0))
                       .build();
    TaskEntity taskEntity = TaskEntity.builder()
                                      .id(1L)
                                      .name("Task Entity")
                                      .description("Description Entity")
                                      .startDate(LocalDateTime.now())
                                      .deadline(LocalDate.of(2015, 7, 25))
                                      .resolveDate(LocalDateTime.of(2011, 4, 7, 1, 23))
                                      .projectEntity(projectEntity)
                                      .build();

    @Test
    void mapEntityToDto() {
        Task taskDtoResult = getTaskMapper().mapEntityToDto(taskEntity);

        Assertions.assertEquals(1L, taskDtoResult.getId());
        Assertions.assertEquals("Task Entity", taskDtoResult.getName());
        Assertions.assertEquals("Description Entity", taskDtoResult.getDescription());
        Assertions.assertEquals(LocalDateTime.now().toLocalDate(), taskDtoResult.getStartDate().toLocalDate());
        Assertions.assertEquals(LocalDate.of(2015, 7, 25), taskDtoResult.getDeadline());
        Assertions.assertEquals(LocalDateTime.of(2011, 4, 7, 1, 23), taskDtoResult.getResolveDate());
        Assertions.assertEquals(2L, taskDtoResult.getProjectId());

    }

    @Test
    void mapDtoToEntity() {
        TaskEntity taskEntityResult = getTaskMapper().mapDtoToEntity(taskDto);

        Assertions.assertEquals("Task DTO", taskEntityResult.getName());
        Assertions.assertEquals("Description DTO", taskEntityResult.getDescription());
        Assertions.assertEquals(LocalDateTime.now().toLocalDate(), taskEntityResult.getStartDate().toLocalDate());
        Assertions.assertEquals(LocalDate.of(2023, 12, 4), taskEntityResult.getDeadline());
        Assertions.assertEquals(LocalDateTime.of(2022, 11, 4, 12, 0), taskEntityResult.getResolveDate());

    }
}