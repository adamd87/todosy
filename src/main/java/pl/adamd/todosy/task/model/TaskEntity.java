package pl.adamd.todosy.task.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import pl.adamd.todosy.project.model.ProjectEntity;

import java.time.LocalDateTime;

@Entity(name = "Task")
@Data
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private LocalDateTime resolveDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;
}
