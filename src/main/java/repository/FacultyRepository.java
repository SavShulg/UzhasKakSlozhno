package repository;

import model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findAllByColorIgnoreCaseOrAndNameIgnoreCase(String color, String name);
}
