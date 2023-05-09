package pl.adamd.todosy.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.adamd.todosy.config.converter.Encrypt;
import pl.adamd.todosy.task.model.TaskEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity(name = "Project")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Convert(converter = Encrypt.class)
    private String name;
    @Convert(converter = Encrypt.class)
    private String description;
    private OffsetDateTime startDate;
    private LocalDate deadline;
    private OffsetDateTime updateDate;
    private OffsetDateTime resolveDate;
    private boolean resolved;
    private boolean allTasksDone;
    @OneToMany(mappedBy = "projectEntity")
    private List<TaskEntity> taskEntityList;

}
