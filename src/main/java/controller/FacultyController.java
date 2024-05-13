package controller;

import model.Faculty;
import model.Student;
import org.springframework.web.bind.annotation.*;
import service.FacultyService;

import java.util.Collection;

public class FacultyController {
    @RestController
    @RequestMapping("/faculty")
    public class FacultyController {
        private final FacultyService service;

        public FacultyController(FacultyService service) {
            this.service = service;
        }

        @GetMapping
        public Faculty get(@RequestParam Long id) {
            return service.get(id);
        }

        @PostMapping
        public Faculty add(@RequestBody Faculty faculty) {
            return service.add(faculty);
        }

        @DeleteMapping
        public boolean delete(@RequestParam Long id) {
            return service.delete(id);
        }

        @PutMapping
        public Faculty update(@RequestBody Faculty faculty) {
            return service.update(faculty);
        }

        @GetMapping("byColorAndName")
        public Collection<Faculty> getByColor(@RequestParam(required = false) String color,
                                              @RequestParam(required = false) String name) {
            if (color == null && name == null) {
                return service.getAll();
            }

            return service.getByColorAndName(color, name);

        }

        @GetMapping("students")
        public Collection<Student> getStudentFaculty(@RequestParam long facultyId) {
            return service.get(facultyId).getStudents();
        }

        @GetMapping("/longestName")
        public String getLongestName() {
            return facultyService.getLongestName();
        }
    }
