package test.task.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import test.task.app.model.Task;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link Task}.
 * Этот интерфейс расширяет интерфейсы {@link JpaRepository} и {@link JpaSpecificationExecutor},
 * предоставляя операции CRUD и возможность выполнения спецификаций для сущности {@link Task}.
 */
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    /**
     * Находит задачу по её заголовку.
     * @param title заголовок задачи
     * @return {@link Optional} содержащий найденную задачу, если она существует, иначе пустой {@link Optional}
     */
    Optional<Task> findByTitle(String title);
}
