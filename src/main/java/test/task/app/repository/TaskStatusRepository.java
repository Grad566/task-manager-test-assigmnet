package test.task.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.task.app.model.TaskStatus;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link TaskStatus}.
 * Этот интерфейс расширяет интерфейс {@link JpaRepository}, предоставляя
 * операции CRUD для сущности {@link TaskStatus}.
 */
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    /**
     * Находит статус задачи по его имени.
     * @param name имя статуса задачи
     * @return {@link Optional} содержащий найденный статус задачи, если он существует, иначе пустой {@link Optional}
     */
    Optional<TaskStatus> findByName(String name);
}
