package test.task.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.task.app.model.Commentary;

/**
 * Репозиторий для управления сущностями {@link Commentary}.
 * Этот интерфейс расширяет интерфейс {@link JpaRepository}, предоставляя
 * операции CRUD и дополнительные методы запросов для сущности {@link Commentary}.
 */
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
}
