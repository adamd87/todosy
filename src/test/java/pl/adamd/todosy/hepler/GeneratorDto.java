package pl.adamd.todosy.hepler;

import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.task.model.Task;

import java.time.LocalDate;

public class GeneratorDto {

    public static Project generateProjectDto(String name, String description, int year, int month, int dayOfMonth) {
        return Project.builder()
                      .name(name)
                      .description(description)
                      .deadline(LocalDate.of(year, month, dayOfMonth))
                      .build();
    }

    public static Task getValidTaskToPost(String name, String description, int year, int month, int dayOfMonth) {
        return Task.builder()
                   .name(name)
                   .description(description)
                   .deadline(LocalDate.of(year, month, dayOfMonth))
                   .build();
    }

    public static Task getInvalidTaskToPost() {
        return Task.builder()
                   .build();
    }
}
