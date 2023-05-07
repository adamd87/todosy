package pl.adamd.todosy.project.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Project extends RepresentationModel<Project> {
    @NotEmpty(message = "Project name is mandatory")
    private String name;
    @NotEmpty(message = "Project description is mandatory")
    private String description;
    private LocalDateTime startDate;
    @NotNull(message = "Project deadline date is mandatory")
    private LocalDate deadline;
    private LocalDateTime resolveDate;
}
