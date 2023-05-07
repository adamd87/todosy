package pl.adamd.todosy.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
public class Project extends RepresentationModel<Project> {

    @NotEmpty(message = "Project name is mandatory")
    private String name;
    @NotEmpty(message = "Project description is mandatory")
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private OffsetDateTime startDate;
    @NotNull(message = "Project deadline date is mandatory")
    private LocalDate deadline;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private OffsetDateTime updateDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private OffsetDateTime resolveDate;
    private boolean resolved;
    private boolean allTasksDone;
}
