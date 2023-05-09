package pl.adamd.todosy.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.adamd.todosy.config.converter.Encrypt;
import pl.adamd.todosy.project.model.ProjectEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity(name = "Task")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Convert(converter = Encrypt.class)
    private String name;
    @Convert(converter = Encrypt.class)
    private String description;
    private OffsetDateTime startDate;
    private LocalDate deadline;
    private OffsetDateTime resolveDate;
    private boolean resolved;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_entity_id")
    private ProjectEntity projectEntity;
}
