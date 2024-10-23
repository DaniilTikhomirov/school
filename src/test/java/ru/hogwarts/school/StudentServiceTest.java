package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

public class StudentServiceTest {
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService();
        studentService.addStudents(new Student("Hogwarts", 12));
    }

    @Test
    public void testGetAllStudents() {
        Assertions.assertEquals(studentService.getStudents().get(1L).getName(), "Hogwarts");
    }

    @Test
    public void testGetStudentById() {
        Assertions.assertEquals(studentService.getStudent(1L).getName(), "Hogwarts");
    }

    @Test
    public void testAddStudent() {
        studentService.addStudents(new Student("Hogwarts", 123));
        Assertions.assertEquals(studentService.getStudents().keySet().size(), 2);
    }

    @Test
    public void testRemoveStudent() {
        studentService.removeStudent(1L);
        Assertions.assertEquals(studentService.getStudents().keySet().size(), 0);
    }

    @Test
    public void testUpdateStudent() {
        studentService.putStudent(1L, new Student("HogwartsNew", 3));
        Assertions.assertEquals(studentService.getStudent(1L).getName(), "HogwartsNew");
    }

    @Test
    public void testUpdateStudentNull(){
        Assertions.assertNull(studentService.putStudent(100L, new Student("Hogwarts", 100)));
    }

    @Test
    public void testGetStudentsByColor() {
        studentService.addStudents(new Student("Hogwarts", 2));
        Assertions.assertEquals(studentService.getFilterStudents(2).getFirst().getAge(),
                2L);
    }
}
