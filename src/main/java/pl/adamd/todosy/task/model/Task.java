package pl.adamd.todosy.task.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@Builder
public class Task extends RepresentationModel<Task> {

    private Long id;
    @NotEmpty(message = "Task name is mandatory")
    private String name;
    @NotEmpty(message = "Task description is mandatory")
    private String description;
    private LocalDateTime startDate;
    @NotNull(message = "Task deadline date is mandatory")
    private LocalDate deadline;
    private LocalDateTime resolveDate;
    private Long projectId;


}
