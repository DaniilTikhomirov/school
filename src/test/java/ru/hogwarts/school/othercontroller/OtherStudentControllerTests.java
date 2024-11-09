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
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.models.Student;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OtherStudentControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url = "http://localhost:";

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void getStudentTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.
                        getForObject(url + port + "/student", String.class))
                .isNotEmpty();
    }

    @Test
    public void getStudentByIDTest() throws Exception {
        Student student = new Student();
        student.setName("asdf");
        Assertions.assertThat(this.testRestTemplate.postForObject(url + port + "/student", student, String.class))
                .isNotNull();


    }

    @Test
    public void putStudentTest() throws Exception {
        Student student = new Student();
        student.setName("asdf");
        student.setId(1L);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> response = testRestTemplate.exchange(url + port + "/student",
                HttpMethod.PUT,
                entity,
                Student.class,
                1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void delStudentTest() throws Exception {
        Student student = new Student();
        student.setName("asdf");
        student.setId(1L);
        ResponseEntity<Student> response = testRestTemplate.exchange(url + port + "/student/1", HttpMethod.DELETE,
                new HttpEntity<>(student), Student.class);
        Assertions.assertThat(response).isNotNull();
    }
}