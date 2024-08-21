package test.task.app.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import test.task.app.model.User;
import test.task.app.repository.UserRepository;

/**
 * Утилита для работы с пользователями.
 * Этот компонент предоставляет методы для получения текущего аутентифицированного
 * пользователя из контекста безопасности. Использует {@link UserRepository} для
 * доступа к данным пользователя.
 */
@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;

    /**
     * Получает текущего аутентифицированного пользователя.
     * @return текущий пользователь, если он аутентифицирован, иначе {@code null}
     */
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }
}
