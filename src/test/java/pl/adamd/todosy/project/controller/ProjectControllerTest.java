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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void GET_should_return_correct_project_name()
            throws Exception {

        projectRepository.save(ProjectEntity.builder()
                                            .name("Project 1")
                                            .description("description")
                                            .deadline(LocalDate.of(2023, 5, 29))
                                            .build());

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

        projectRepository.save(ProjectEntity.builder()
                                            .name("Project 1")
                                            .description("description")
                                            .deadline(LocalDate.of(2023, 5, 29))
                                            .build());

        projectRepository.save(ProjectEntity.builder()
                                            .name("Project 2")
                                            .description("description")
                                            .deadline(LocalDate.of(2024, 12, 13))
                                            .build());

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
           .andExpect(jsonPath("body").value("Project not found with projectId 2"))
           .andExpect(jsonPath("statusCode").value("NOT_FOUND"))
           .andExpect(jsonPath("statusCodeValue").value(404));
    }

    @Test
    void POST_add_invalid_project_should_return_error_list()
            throws Exception {

        Project project = Project.builder()
                                 .name("Project1")
                                 .build();
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
        Project project = Project.builder()
                                 .name("Project1")
                                 .description("Description")
                                 .deadline(LocalDate.of(2023, 5, 29))
                                 .build();

        mvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(project)))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
           .andExpect(jsonPath("headers").isEmpty())
           .andExpect(jsonPath("body.name").value("Project1"))
           .andExpect(jsonPath("body.description").value("Description"))
           .andExpect(jsonPath("body.startDate").isNotEmpty())
           .andExpect(jsonPath("body.deadline").value("2023-05-29"))
           .andExpect(jsonPath("body.resolveDate").isEmpty())
           .andExpect(jsonPath("body.links").isArray())
           .andExpect(jsonPath("body.links").isNotEmpty())
           .andExpect(jsonPath("body.links[0].rel").value("self"))
           .andExpect(jsonPath("body.links[0].href").value("http://localhost/projects/1"))
           .andExpect(jsonPath("statusCode").value("OK"))
           .andExpect(jsonPath("statusCodeValue").value(200));
    }

}