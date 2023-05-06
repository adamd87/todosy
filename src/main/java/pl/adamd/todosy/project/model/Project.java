package pl.adamd.todosy.project.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Project extends RepresentationModel<Project> {

    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private LocalDateTime resolveDate;
}
