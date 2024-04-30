package service;

import model.Student;
import org.springframework.stereotype.Service;
import repository.StudentRepository;

import java.util.Collection;

public class StudentService {

    @Service
    public class StudentService {
        private final StudentRepository repository;
        private final StudentRepository studentRepository;

        public StudentService(StudentRepository repository, StudentRepository studentRepository) {
            this.repository = repository;
            this.studentRepository = studentRepository;
        }


        public Student add(Student student) {
            return repository.save(student);
        }

        public Student get(long id) {
            return repository.findById(id).orElseThrow(RecordNotFoundException::new);


        }

        public boolean delete(long id) {
            return repository.findById(id)
                    .map(entity -> {
                        repository.delete(entity);
                        return true;
                    }).orElse(false);
        }

        public Student update(Student student) {
            return repository.findById(student.getId())
                    .map(entity -> {
                        return repository.save(student);
                    })
                    .orElse(null);
        }

        public Collection<Student> getByAgeBetween(int min, int max) {
            return repository.findAllByAgeBetween(min, max);
        }

        public Collection<Student> getAll() {
            return repository.findAll();
        }

        public int getStudentCount() {
            return studentRepository.countStudents();
        }
        public double getAvgAge() {
            return studentRepository.avgAge();
        }

        public Collection<Student> getLastFive() {
            return studentRepository.getLastFive();
        }
}
