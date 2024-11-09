package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private final String name = "hog";
    private final int age = 12;

    @Test
    public void addStudent() throws Exception {
        JSONObject studentObj = new JSONObject();
        studentObj.put("name", name);
        studentObj.put("age", age);
        studentObj.put("faculty", null);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setFaculty(null);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void findFaculty() throws Exception {
        JSONObject studentObj = new JSONObject();
        studentObj.put("name", name);
        studentObj.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentRepository
                .findStudentsByAgeBetween(any(Integer.class), any(Integer.class)))
                .thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/find/between/age?min=10&max=20")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));


    }

    @Test
    public void delFacultyTest() throws Exception {
        doNothing().when(studentRepository).deleteById(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    public void updateFacultyTest() throws Exception {
        long id = 1L;
        JSONObject studentObj = new JSONObject();
        studentObj.put("name", name);
        studentObj.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    public void findFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName(name);
        faculty.setColor("red");
        JSONObject studentObj = new JSONObject();
        studentObj.put("name", name);
        studentObj.put("age", age);
        studentObj.put("faculty", faculty);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/faculty/1")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value("red"));


    }

    @Test
    public void averageAgeTest() throws Exception {
        when(studentRepository.getAverageAgeStudents()).thenReturn(12.21);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/average/age")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(12.21));
    }

    @Test
    public void limitTest() throws Exception {
        JSONObject studentObj = new JSONObject();
        studentObj.put("name", name);
        studentObj.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        when(studentRepository.getLastStudent(any(Integer.class))).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/last/student/1")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

    }

    @Test
    public void countTest() throws Exception {
        when(studentRepository.countStudent()).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/count/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));

    }
}