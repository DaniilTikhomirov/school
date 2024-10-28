package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;

    }

    public Collection<Faculty> getFaculties() {
        return this.facultyRepository.findAll();
    }

    public Faculty getFaculty(Long id) {
        return this.facultyRepository.findById(id).orElse(null);
    }

    public Faculty addFaculty(Faculty faculty) {
        return this.facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        this.facultyRepository.deleteById(id);
    }

    public Faculty putFaculty(Faculty faculty) {
        return this.facultyRepository.save(faculty);
    }
}