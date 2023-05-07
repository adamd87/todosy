package pl.adamd.todosy.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.adamd.todosy.TodosyApplication;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.repository.ProjectRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TodosyApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ProjectControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ProjectRepository projectRepository;

    @Test
    void GET_should_return_correct_project_name()
            throws Exception {

        projectRepository.save(ProjectEntity.builder()
                                            .name("Project 1")
                                            .id(1L)
                                            .build());

        mvc.perform(get("/projects/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/json")))
                .andExpect(jsonPath("body.name").value("Project 1"));
    }

    @Test
    void addProject() {


    }
}