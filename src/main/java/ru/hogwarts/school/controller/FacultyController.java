package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Faculty>> getStudent() {
        return ResponseEntity.ok(facultyService.getFaculties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getStudent(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping()
    public ResponseEntity<Faculty> addStudent(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.addFaculty(faculty));
    }

    @PutMapping()
    public ResponseEntity<Faculty> putStudent(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.putFaculty(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delStudent(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(value = "name", required = false) String name,
                                                             @RequestParam(value = "color", required = false) String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByNameOrColor(name, color));
    }


    @GetMapping("get/students/{id}")
    public ResponseEntity<Set<Student>> getStudents(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getStudentsFromFaculty(id));
    }

    @GetMapping("get/students/count/{id}")
    public ResponseEntity<Integer> countStudents(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getStudentsCount(id));
    }

    @GetMapping("get/max/name")
    public ResponseEntity<String> getMaxName() {
        return ResponseEntity.ok(facultyService.getMaxLengthName());
    }

}
