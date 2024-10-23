package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.service.FacultyService;

public class FacultyServiceTest {
    private FacultyService facultyService;

    @BeforeEach
    public void setUp() {
        facultyService = new FacultyService();
        facultyService.addFaculty(new Faculty("Hogwarts", "Hogwarts"));
    }

    @Test
    public void testGetAllFaculties() {
        Assertions.assertEquals(facultyService.getFaculties().get(1L).getName(), "Hogwarts");
    }

    @Test
    public void testGetFacultyById() {
        Assertions.assertEquals(facultyService.getFaculty(1L).getName(), "Hogwarts");
    }

    @Test
    public void testAddFaculty() {
        facultyService.addFaculty(new Faculty("Hogwarts", "Hogwarts"));
        Assertions.assertEquals(facultyService.getFaculties().keySet().size(), 2);
    }

    @Test
    public void testRemoveFaculty() {
        facultyService.removeFaculty(1L);
        Assertions.assertEquals(facultyService.getFaculties().keySet().size(), 0);
    }

    @Test
    public void testUpdateFaculty() {
        facultyService.putFaculty(1L, new Faculty("HogwartsNew", "Hogwarts"));
        Assertions.assertEquals(facultyService.getFaculty(1L).getName(), "HogwartsNew");
    }

    @Test
    public void testUpdateFacultyNull(){
        Assertions.assertNull(facultyService.putFaculty(100L, new Faculty("a", "ase")));
    }

    @Test
    public void testGetFacultiesByColor() {
        facultyService.addFaculty(new Faculty("Hogwarts", "HogwartsNew"));
        Assertions.assertEquals(facultyService.getFilterFaculty("Hogwarts").getFirst().getColor(),
                "Hogwarts");
    }

}
