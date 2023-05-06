package pl.adamd.todosy.task.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
public class Task extends RepresentationModel<Task> {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private LocalDateTime resolveDate;
    private Long projectId;


}
