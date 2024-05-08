package service;

import model.Faculty;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.FacultyRepository;

import java.util.Collection;
import java.util.logging.Logger;


@Service
    public class FacultyService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(FacultyService.class);

        private final FacultyRepository repository;

        public FacultyService(FacultyRepository repository) {
            this.repository = repository;
        }


        public Faculty add(Faculty faculty) {
            return repository.save(faculty);
            logger.info("faculty was added!");
        }

        public Faculty get(long id) {
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

        public Faculty update(Faculty faculty) {
            return repository.findById(faculty.getId())
                    .map(entity -> {
                        return repository.save(faculty);
                    })
                    .orElse(null);
            logger.info("the file has been update...");
        }

        public Collection<Faculty> getByColorAndName(String color, String name) {
            return repository.findAllByColorIgnoreCaseOrAndNameIgnoreCase(color, name);
        }

        public Collection<Faculty> getAll() {
            return repository.findAll();
        }
    }
