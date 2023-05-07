package pl.adamd.todosy.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import pl.adamd.todosy.task.model.TaskEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDate deadline;
    private LocalDateTime resolveDate;
    @OneToMany(mappedBy = "projectEntity")
    private List<TaskEntity> taskEntityList;

}
