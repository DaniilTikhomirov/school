package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setName("Hogwarts");
    }

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;


    @Test
    public void testGetAllStudent() {
        when(studentRepository.findAll()).thenReturn(new ArrayList<>(List.of(student)));
        Assertions.assertEquals(studentService.getStudents().size(), 1);
    }

    @Test
    public void testGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Assertions.assertEquals(studentService.getStudent(1L).getName(), "Hogwarts");
    }

    @Test
    public void testAddStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        Assertions.assertEquals(studentService.addStudents(student).getName(), student.getName());
    }

    @Test
    public void testUpdateStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        Assertions.assertEquals(studentService.putStudent(student).getName(), student.getName());
    }
}