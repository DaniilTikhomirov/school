package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public Student getStudent(long id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    public Student addStudents(Student student) {
        return this.studentRepository.save(student);
    }

    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentBetweenAge(int startAge, int endAge) {
        return studentRepository.findStudentsByAgeBetween(startAge, endAge);
    }

    public Faculty getStudentFaculty(Long id) {
        return getStudent(id).getFaculty();
    }

    public Student putStudent(Student student) {
        return this.studentRepository.save(student);
    }

    public Double getAverageStudentAge() {
        return studentRepository.getAverageAgeStudents();
    }

    public Collection<Student> getLastStudents(Integer limit) {
        return studentRepository.getLastStudent(limit);
    }

    public Integer countStudent(){
        return studentRepository.countStudent();
    }
}
