package pl.adamd.todosy.project.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import pl.adamd.todosy.task.model.TaskEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Project")
@Data
@Builder
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private LocalDateTime resolveDate;
    @OneToMany(mappedBy = "project")
    private List<TaskEntity> taskEntityList;

}
