package ru.hogwarts.school.othercontroller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.models.Faculty;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OtherFacultyControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url = "http://localhost:";

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void getStudentTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.
                        getForObject(url + port + "/faculty", String.class))
                .isNotEmpty();
    }

    @Test
    public void getStudentByIDTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("asdf");
        Assertions.assertThat(this.testRestTemplate.postForObject(url + port + "/faculty", faculty, String.class))
                .isNotNull();


    }

    @Test
    public void putStudentTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("asdf");
        faculty.setId(1L);
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> response = testRestTemplate.exchange(url + port + "/faculty",
                HttpMethod.PUT,
                entity,
                Faculty.class,
                1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void delStudentTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("asdf");
        faculty.setId(1L);
        ResponseEntity<Faculty> response = testRestTemplate.exchange(url + port + "/faculty/1", HttpMethod.DELETE,
                new HttpEntity<>(faculty), Faculty.class);
        Assertions.assertThat(response).isNotNull();
    }
}