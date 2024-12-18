package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    private final Object flag = new Object();

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Student>> getStudent() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentByID(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping()
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudents(student));
    }

    @PutMapping()
    public ResponseEntity<Student> putStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.putStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delStudent(@PathVariable Long id) {
        this.studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("find/between/age")
    public ResponseEntity<Collection<Student>> findBetweenAge(@RequestParam("min") Integer min,
                                                              @RequestParam("max") Integer max) {
        return ResponseEntity.ok(studentService.getStudentBetweenAge(min, max));
    }

    @GetMapping("get/faculty/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentFaculty(id));
    }

    @GetMapping("get/average/age")
    public ResponseEntity<Double> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageStudentAge());
    }

    @GetMapping("get/last/student/{limit}")
    public ResponseEntity<Collection<Student>> getLastStudent(@PathVariable Integer limit) {
        return ResponseEntity.ok(studentService.getLastStudents(limit));
    }

    @GetMapping("count/student")
    public ResponseEntity<Integer> countStudent(){
        return ResponseEntity.ok(studentService.countStudent());
    }

    @GetMapping("get/sort/student/{symbol}")
    public ResponseEntity<Collection<String>> getSortStudent(@PathVariable String symbol) {
        return ResponseEntity.ok(studentService.getAllNamesStartSymbol(symbol));
    }

    @GetMapping("get/average")
    public ResponseEntity<Double> getAverage() {
        return ResponseEntity.ok(studentService.getAverageStudentAge());
    }


    @GetMapping("/students/print-parallel")
    public ResponseEntity<String> printParallel() {
        List<Student> students = studentService.getStudents().stream().toList();
        if(students.size() < 6){
            return ResponseEntity.ok("Add student");
        }

        System.out.println(students.getFirst());
        System.out.println(students.get(1));

        new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        }).start();

        return ResponseEntity.ok("finish");
    }

    @GetMapping("/students/print-synchronized")
    public ResponseEntity<String> printSynchronized() {
        List<Student> students = studentService.getStudents().stream().toList();
        if(students.size() < 6){
            return ResponseEntity.ok("Add student");
        }

        printStudentParallel(0, students);
        printStudentParallel(1, students);

        new Thread(() -> {
            printStudentParallel(2, students);
            printStudentParallel(3, students);
        }).start();

        new Thread(() -> {
            printStudentParallel(4, students);
            printStudentParallel(5, students);
        }).start();

        return ResponseEntity.ok("finish");
    }

    private void printStudentParallel(int index, List<Student> students) {
        synchronized (flag){
        System.out.println(students.get(index));
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    }
}
