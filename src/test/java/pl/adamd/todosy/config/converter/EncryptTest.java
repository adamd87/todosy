package pl.adamd.todosy.config.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.adamd.todosy.TodosyApplication;
import pl.adamd.todosy.project.model.ProjectEntity;
import pl.adamd.todosy.project.repository.ProjectRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TodosyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class EncryptTest {

    private static final String NAME = "Task Name";
    private static final String DESCRIPTION = "Task description";

    private final Long id = 1L;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProjectRepository projectRepository;

    @BeforeEach
    public void setUp() {
       projectRepository.save(ProjectEntity.builder()
                     .name(NAME)
                     .description(DESCRIPTION)
                     .build());
    }

    @Test
    public void read_encrypted_should_return_encrypted_value_on_db() {
        ProjectEntity projectEntity = jdbcTemplate.queryForObject(
                "select * from project where id = ?",
                (resultSet, i) -> {
                    ProjectEntity result = new ProjectEntity();
                    result.setId(resultSet.getLong("id"));
                    result.setName(resultSet.getString("name"));
                    result.setDescription(resultSet.getString("description"));
                    return result;
                },
                id
        );
        assert projectEntity != null;
        assertThat(projectEntity.getName()).isNotEqualTo(NAME);
        assertThat(projectEntity.getDescription()).isNotEqualTo(DESCRIPTION);
    }

    @Test
    public void read_decrypted_should_return_decrypted_value_from_repository() {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow();
        assertThat(projectEntity.getName()).isEqualTo(NAME);
        assertThat(projectEntity.getDescription()).isEqualTo(DESCRIPTION);
    }
}
