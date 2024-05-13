package controller;

import model.Faculty;
import model.Student;
import org.springframework.web.bind.annotation.*;
import service.StudentService;

import java.util.Collection;

public class StudentController {

    @RestController
    @RequestMapping("/student")
    public class StudentController {
        private final StudentService service;
        private final StudentService studentService;

        public StudentController(StudentService service, StudentService studentService) {
            this.service = service;
            this.studentService = studentService;
        }

        @GetMapping
        public Student get(@RequestParam Long id) {
            return service.get(id);
        }

        @PostMapping
        public Student add(@RequestBody Student student) {
            return service.add(student);
        }

        @DeleteMapping
        public boolean delete(@RequestParam Long id) {
            return service.delete(id);
        }

        @PutMapping
        public Student update(@RequestBody Student student) {
            return service.update(student);
        }

        @GetMapping("/byAge")
        public Collection<Student> getByAgeBetween(@RequestParam(required = false) Integer min,
                                                   @RequestParam(required = false) Integer max) {
            if(min!= null && max !=null){
                return service.getByAgeBetween(min, max);
            }
            return service.getAll();
        }
        @GetMapping("faculty")
        public Faculty getStudentFaculty(@RequestParam long studentId){
            return service.get(studentId).getFaculty();
        }

        @GetMapping("/count")
        public int getStudentCount() {
            return studentService.getStudentCount();
        }
        @GetMapping("/avg-age")
        public double getAvgAge() {
            return studentService.getAvgAge();
        }
        @GetMapping("/last")
        public Collection<Student> getLastStudents() {
            return studentService.getLastFive();
        }
        @GetMapping("/nameStartsA")
        public Collection<String> getStudentNameStartsA() {
            return studentService.getNameStartsWithA();
        }
        @GetMapping("/avg-age-stream")
        public double getAgeStream() {
            return studentService.getAverageAge();
        }

        @GetMapping("/print-parallel")
        public void printParallel() {
            studentService.printParallel();
        }
        @GetMapping("/print-sync")
        public void printSync () {
            studentService.printSync();
        }
        }
    }
