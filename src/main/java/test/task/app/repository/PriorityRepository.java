package test.task.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.task.app.model.Priority;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link Priority}.
 * Этот интерфейс расширяет интерфейс {@link JpaRepository}, предоставляя
 * операции CRUD и дополнительные методы запросов для сущности {@link Priority}.
 */
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    /**
     * Находит приоритет по его имени.
     * @param name имя приоритета
     * @return {@link Optional} содержащий найденный приоритет, если он существует, иначе пустой {@link Optional}
     */
    Optional<Priority> findByName(String name);
}
