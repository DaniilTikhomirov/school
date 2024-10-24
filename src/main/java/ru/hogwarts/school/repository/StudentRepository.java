package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
