package pl.adamd.todosy.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.adamd.todosy.project.model.ProjectEntity;

import java.time.LocalDateTime;

@Entity(name = "Task")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "project_entity_id")
    private ProjectEntity projectEntity;
}
