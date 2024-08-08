package test.task.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.task.app.model.Commentary;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
}
