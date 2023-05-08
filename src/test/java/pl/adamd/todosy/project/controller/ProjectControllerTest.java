package pl.adamd.todosy.project.controller;

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
import pl.adamd.todosy.project.model.Project;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.repository.ProjectRepository;
import pl.adamd.todosy.task.model.Task;
import pl.adamd.todosy.task.service.TaskViewService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.adamd.todosy.hepler.GeneratorDto.generateProjectDto;
import static pl.adamd.todosy.hepler.GeneratorDto.getValidTaskToPost;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TodosyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ProjectControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ObjectMapper objectMapper;


    private void inputProjectToRepository(String name, int year, int month, int dayOfMonth) {
        projectRepository.save(ProjectEntity.builder()
                                            .name(name)
                                            .description("description")
                                            .deadline(LocalDate.of(year, month, dayOfMonth))
                                            .build());
    }

    @Test
    void GET_should_return_correct_project_name()
            throws Exception {

        inputProjectToRepository("Project 1", 2023, 5, 29);

        mvc.perform(get("/projects/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("body.name").value("Project 1"))
           .andExpect(jsonPath("body.description").value("description"))
           .andExpect(jsonPath("body.deadline").value("2023-05-29"));
    }


    @Test
    void GET_second_project_should_return_correct_project_name()
            throws Exception {

        inputProjectToRepository("Project 1", 2023, 5, 29);
        inputProjectToRepository("Project 2", 2024, 12, 13);

        mvc.perform(get("/projects/2").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("body.name").value("Project 2"))
           .andExpect(jsonPath("body.description").value("description"))
           .andExpect(jsonPath("body.deadline").value("2024-12-13"));
    }

    @Test
    void GET_incorrect_project_id_should_return_validation_massage_content_Json()
            throws Exception {

        mvc.perform(get("/projects/2").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body").value("Project not found with projectID: 2"))
           .andExpect(jsonPath("statusCode").value("NOT_FOUND"))
           .andExpect(jsonPath("statusCodeValue").value(404));
    }

    @Test
    void POST_add_invalid_project_should_return_error_list()
            throws Exception {

        Project project = generateProjectDto("Project 1", null, 2023, 6, 11);

        mvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(project)))
           .andExpect(status().isBadRequest())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("errors").isArray())
           .andExpect(jsonPath("errors").isNotEmpty());
    }


    @Test
    void POST_add_valid_project_should_return_correct_response()
            throws Exception {

        Project project = generateProjectDto("Project1", "Description", 2023, 5, 11);

        mvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(project)))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body.name").value("Project1"))
           .andExpect(jsonPath("body.description").value("Description"))
           .andExpect(jsonPath("body.startDate").value(OffsetDateTime.now()
                                                                     .format(DateTimeFormatter.ofPattern(
                                                                             "yyyy-MM-dd'T'HH:mm"))))
           .andExpect(jsonPath("body.deadline").value("2023-05-11"))
           .andExpect(jsonPath("body.resolveDate").isEmpty())
           .andExpect(jsonPath("body.updateDate").isEmpty())
           .andExpect(jsonPath("body.resolved").value(false))
           .andExpect(jsonPath("body.allTasksDone").value(false))
           .andExpect(jsonPath("body.links").isArray())
           .andExpect(jsonPath("body.links").isNotEmpty())
           .andExpect(jsonPath("body.links[0].rel").value("self"))
           .andExpect(jsonPath("body.links[0].href").value("http://localhost/projects/1"))
           .andExpect(jsonPath("statusCode").value("OK"))
           .andExpect(jsonPath("statusCodeValue").value(200));
    }

    @Test
    void POST_close_project_should_return_correct_response()
            throws Exception {
        inputProjectToRepository("Project 1", 2023, 5, 29);

        mvc.perform(post("/projects/close/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("body.resolveDate").value(OffsetDateTime.now()
                                                                       .format(DateTimeFormatter.ofPattern(
                                                                               "yyyy-MM-dd'T'HH:mm"))))
           .andExpect(jsonPath("body.resolved").value(true))
           .andExpect(jsonPath("body.links[0].rel").value("self"))
           .andExpect(jsonPath("body.links[0].href").value("http://localhost/projects/1"))
           .andExpect(jsonPath("statusCode").value("OK"))
           .andExpect(jsonPath("statusCodeValue").value(200));
    }

    @Test
    void DELETE_delete_project_with_success()
            throws Exception {
        inputProjectToRepository("Project 1", 2023, 5, 29);

        mvc.perform(delete("/projects/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body").value(true))
           .andExpect(jsonPath("statusCode").value("OK"))
           .andExpect(jsonPath("statusCodeValue").value(200));
    }

    @Test
    void DELETE_delete_project_with_unresolved_task_should_failed_response()
            throws Exception {
        inputProjectToRepository("Project 1", 2023, 5, 29);
        Task task = getValidTaskToPost("Task 1", "description", 2023, 5, 29);
        mvc.perform(post("/tasks/project/1/add").contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(task)));

        mvc.perform(delete("/projects/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body").value("Project has open tasks, close all task first, tasks ids: [1]"))
           .andExpect(jsonPath("statusCode").value("CONFLICT"))
           .andExpect(jsonPath("statusCodeValue").value(409));
    }


}