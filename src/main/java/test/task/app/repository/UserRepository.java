package test.task.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.task.app.model.User;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link User}.
 * Этот интерфейс расширяет интерфейс {@link JpaRepository}, предоставляя
 * операции CRUD для сущности {@link User}.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Находит пользователя по его электронной почте.
     * @param email электронная почта пользователя
     * @return {@link Optional} содержащий найденного пользователя, если он существует, иначе пустой {@link Optional}
     */
    Optional<User> findByEmail(String email);
}
