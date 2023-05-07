package pl.adamd.todosy.task.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Setter
@Getter
@Builder
public class Task extends RepresentationModel<Task> {

    private Long id;
    @NotEmpty(message = "Task name is mandatory")
    private String name;
    @NotEmpty(message = "Task description is mandatory")
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private OffsetDateTime startDate;
    @NotNull(message = "Task deadline date is mandatory")
    private LocalDate deadline;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private OffsetDateTime resolveDate;
    private boolean resolved;
    private Long projectId;


}
