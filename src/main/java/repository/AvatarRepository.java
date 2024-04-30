package repository;

import model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
        Optional<Avatar> findAllByStudentId(Long studentId);
}
