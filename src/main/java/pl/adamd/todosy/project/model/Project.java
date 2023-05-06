package pl.adamd.todosy.project.model;

import lombok.Builder;
import lombok.Data;
import pl.adamd.todosy.task.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Project {

    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private LocalDateTime resolveDate;
    private List<Task> taskList;
}
