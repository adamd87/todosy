package pl.adamd.todosy.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.adamd.todosy.TodosyApplication;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.repository.ProjectRepository;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.model.TaskEntity;
import pl.adamd.todosy.task.repository.TaskRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.adamd.todosy.hepler.GeneratorDto.getInvalidTaskToPost;
import static pl.adamd.todosy.hepler.GeneratorDto.getValidTaskToPost;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TodosyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class TaskControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void GET_correct_task_by_valid_id_from_repo()
            throws Exception {
        ProjectEntity projectEntity = getProjectEntity();
        inputTaskToRepo(projectEntity);

        mvc.perform(get("/tasks/get/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body.id").value(1))
           .andExpect(jsonPath("body.name").value("Task 1"))
           .andExpect(jsonPath("body.description").value("description"))
           .andExpect(jsonPath("body.deadline").value("2023-05-29"))
           .andExpect(jsonPath("body.resolveDate").isEmpty())
           .andExpect(jsonPath("body.links").isArray())
           .andExpect(jsonPath("body.links").isNotEmpty())
           .andExpect(jsonPath("body.links[0].rel").value("project"))
           .andExpect(jsonPath("body.links[0].href").value("http://localhost/projects/1"))
           .andExpect(jsonPath("body.links[1].rel").value("self"))
           .andExpect(jsonPath("body.links[1].href").value("http://localhost/tasks/get/1"))
           .andExpect(jsonPath("statusCode").value("OK"))
           .andExpect(jsonPath("statusCodeValue").value(200));
    }

    @Test
    void GET_incorrect_task_by_invalid_id_from_repo()
            throws Exception {

        mvc.perform(get("/tasks/get/2").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body").value("Task not found with taskID: 2"))
           .andExpect(jsonPath("statusCode").value("NOT_FOUND"))
           .andExpect(jsonPath("statusCodeValue").value(404));
    }

    @Test
    void POST_correct_request_body_task_should_return_valid_response()
            throws Exception {
        getProjectEntity();
        Task task = getValidTaskToPost("Task 1", "description", 2023, 5, 29);

        mvc.perform(post("/tasks/project/1/add").contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(task)))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body.id").value(1))
           .andExpect(jsonPath("body.name").value("Task 1"))
           .andExpect(jsonPath("body.description").value("description"))
           .andExpect(jsonPath("body.startDate").value(OffsetDateTime.now()
                                                                     .format(DateTimeFormatter.ofPattern(
                                                                             "yyyy-MM-dd'T'HH:mm"))))
           .andExpect(jsonPath("body.deadline").value("2023-05-29"))
           .andExpect(jsonPath("body.resolveDate").isEmpty())
           .andExpect(jsonPath("body.projectId").value(1))
           .andExpect(jsonPath("body.links").isArray())
           .andExpect(jsonPath("body.links").isNotEmpty())
           .andExpect(jsonPath("body.links[0].rel").value("project"))
           .andExpect(jsonPath("body.links[0].href").value("http://localhost/projects/1"))
           .andExpect(jsonPath("body.links[1].rel").value("self"))
           .andExpect(jsonPath("body.links[1].href").value("http://localhost/tasks/get/1"))
           .andExpect(jsonPath("statusCode").value("OK"))
           .andExpect(jsonPath("statusCodeValue").value(200));
    }


    @Test
    void POST_incorrect_request_body_task_should_return_error_response()
            throws Exception {
        Task task = getInvalidTaskToPost();

        mvc.perform(post("/tasks/project/1/add").contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(task)))
           .andExpect(status().isBadRequest())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("errors").isArray())
           .andExpect(jsonPath("errors").isNotEmpty());

    }

    @Test
    void POST_resolve_task_by_id_correct()
            throws Exception {
        ProjectEntity projectEntity = getProjectEntity();
        inputTaskToRepo(projectEntity);

        mvc.perform(post("/tasks/resolve/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body.id").value(1))
           .andExpect(jsonPath("body.name").value("Task 1"))
           .andExpect(jsonPath("body.description").value("description"))
           .andExpect(jsonPath("body.startDate").value(OffsetDateTime.now()
                                                                     .format(DateTimeFormatter.ofPattern(
                                                                             "yyyy-MM-dd'T'HH:mm"))))
           .andExpect(jsonPath("body.deadline").value("2023-05-29"))
           .andExpect(jsonPath("body.resolveDate").value(OffsetDateTime.now()
                                                                       .format(DateTimeFormatter.ofPattern(
                                                                               "yyyy-MM-dd'T'HH:mm"))))
           .andExpect(jsonPath("body.resolved").value(true))
           .andExpect(jsonPath("body.projectId").value(1))
           .andExpect(jsonPath("body.links").isArray())
           .andExpect(jsonPath("body.links").isNotEmpty())
           .andExpect(jsonPath("body.links[0].rel").value("project"))
           .andExpect(jsonPath("body.links[0].href").value("http://localhost/projects/1"))
           .andExpect(jsonPath("body.links[1].rel").value("self"))
           .andExpect(jsonPath("body.links[1].href").value("http://localhost/tasks/get/1"))
           .andExpect(jsonPath("statusCode").value("OK"))
           .andExpect(jsonPath("statusCodeValue").value(200));

    }


    private void inputTaskToRepo(ProjectEntity projectEntity) {
        taskRepository.save(TaskEntity.builder()
                                      .name("Task 1")
                                      .description("description")
                                      .startDate(OffsetDateTime.now())
                                      .deadline(LocalDate.of(2023, 5, 29))
                                      .projectEntity(projectEntity)
                                      .build());
    }

    private ProjectEntity getProjectEntity() {
        return projectRepository.save(ProjectEntity.builder()
                                                   .name("Project 1")
                                                   .description("description")
                                                   .startDate(OffsetDateTime.now())
                                                   .deadline(LocalDate.of(2023, 5, 29))
                                                   .build());
    }


}