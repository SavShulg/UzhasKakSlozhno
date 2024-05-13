package service;

import model.Student;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.StudentRepository;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StudentService {

    @Service
    public class StudentService {
        private final StudentRepository repository;
        private final StudentRepository studentRepository;

        private final static Logger logger = (Logger) LoggerFactory.getLogger(StudentService.class);

        public StudentService(StudentRepository repository, StudentRepository studentRepository) {
            this.repository = repository;
            this.studentRepository = studentRepository;
        }


        public Student add(Student student) {
            return repository.save(student);
            logger.info("student was added!");
        }

        public Student get(long id) {
            return repository.findById(id).orElseThrow(RecordNotFoundException::new);
            logger.info("the file has been transferred...");
        }

        public boolean delete(long id) {
            return repository.findById(id)
                    .map(entity -> {
                        repository.delete(entity);
                        return true;
                    }).orElse(false);
            logger.info("the file has been delete...");
        }

        public Student update(Student student) {
            return repository.findById(student.getId())
                    .map(entity -> {
                        return repository.save(student);
                    })
                    .orElse(null);
            logger.info("the file has been update...");
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

        public Collection<String> getNameStartsWithA() {
            return studentRepository.findAll().stream()
                    .map(s -> s.getName())
                    .map(String::toUpperCase)
                    .filter(name -> name.startsWiht("A"))
                    .sorted()
                    .collect(Collectors.toList());
        }

        public double getAverageAge() {
            return studentRepository.findAll().stream()
                    .mapToDouble (Student::getAge)
                    .average()
                    .orElse(0);
        }
    }
}
