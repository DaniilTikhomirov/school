package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAgeBetween(int age, int age2);

    @Query(value = "SELECT AVG(student.age) FROM student", nativeQuery = true)
    Double getAverageAgeStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    Collection<Student> getLastStudent(Integer limit);
}
