package test.task.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.task.app.model.Priority;

import java.util.Optional;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
    Optional<Priority> findByName(String name);
}
