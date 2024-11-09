package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getStudents() {
        logger.info("Get students");
        return this.studentRepository.findAll();
    }

    public Student getStudent(long id) {
        logger.info("Get student with id {}", id);
        return this.studentRepository.findById(id).orElse(null);
    }

    public Student addStudents(Student student) {
        logger.info("Add student {}", student);
        return this.studentRepository.save(student);
    }

    public void removeStudent(Long id) {
        logger.info("Remove student with id {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentBetweenAge(int startAge, int endAge) {
        logger.info("Get student between age {} and age {}", startAge, endAge);
        return studentRepository.findStudentsByAgeBetween(startAge, endAge);
    }

    public Faculty getStudentFaculty(Long id) {
        logger.info("Get student faculty with id {}", id);
        return getStudent(id).getFaculty();
    }

    public Student putStudent(Student student) {
        logger.info("Put student {}", student);
        return this.studentRepository.save(student);
    }

    public Double getAverageStudentAge() {
        logger.info("get average studentAge");
        return studentRepository.getAverageAgeStudents();
    }

    public Collection<Student> getLastStudents(Integer limit) {
        logger.info("Get last students");
        return studentRepository.getLastStudent(limit);
    }

    public Integer countStudent(){
        logger.info("Count students");
        return studentRepository.countStudent();
    }

    public Collection<String> getAllNamesStartSymbol(String symbol){
        logger.info("Get all names start symbol {}", symbol);

        Collection<Student> students = getStudents();

        return students.stream().
                parallel().
                filter(o -> o.getName().toLowerCase().startsWith(symbol.toLowerCase())).
                map(o -> o.getName().toUpperCase()).
                sorted().toList();
    }

    public Double getAverageAge(){
        logger.info("Get average age");
        Collection<Student> students = getStudents();

        return students.
                stream().
                parallel().
                mapToInt(Student::getAge).
                average().orElse(0.0);
    }
}
