package test.task.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import test.task.app.model.User;
import test.task.app.repository.UserRepository;

/**
 * Сервис для управления пользователями, реализующий интерфейс UserDetailsManager.
 * Предоставляет методы для загрузки, создания, обновления и удаления пользователей.
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Загружает пользователя по его имени пользователя (email).
     * @param username Имя пользователя (email) для поиска.
     * @return Объект UserDetails, представляющий найденного пользователя.
     * @throws UsernameNotFoundException Если пользователь с указанным именем не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }

    /**
     * Создает нового пользователя.
     * @param userData Данные пользователя, которого нужно создать.
     */
    @Override
    public void createUser(UserDetails userData) {
        var user = new User();
        user.setEmail(userData.getUsername());
        var hashedPassword = passwordEncoder.encode(userData.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
