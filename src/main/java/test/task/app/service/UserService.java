package test.task.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.task.app.dto.userDTO.UserCreatedDTO;
import test.task.app.dto.userDTO.UserDTO;
import test.task.app.dto.userDTO.UserUpdatedDTO;
import test.task.app.exception.ResourceNotFoundException;
import test.task.app.mapper.UserMapper;
import test.task.app.repository.UserRepository;

import java.util.List;

/**
 * Сервисный класс для управления пользователями.
 * Этот класс предоставляет методы для выполнения операций CRUD над пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Получает всех пользователей.
     * @return список UserDTO, представляющий всех пользователей.
     */
    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    /**
     * Получает конкретного пользователя по его ID.
     * @param id ID пользователя для получения
     * @return UserDTO, представляющий пользователя.
     * @throws ResourceNotFoundException если пользователь с данным ID не найден.
     */
    public UserDTO show(Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));
        return mapper.map(user);
    }

    /**
     * Создает нового пользователя.
     * @param data объект передачи данных, содержащий информацию для нового пользователя.
     * @return UserDTO, представляющий созданного пользователя.
     */
    public UserDTO create(UserCreatedDTO data) {
        var user = mapper.map(data);
        repository.save(user);
        return mapper.map(user);
    }

    /**
     * Обновляет существующего пользователя.
     * @param data объект передачи данных, содержащий обновленную информацию для пользователя.
     * @param id ID пользователя для обновления.
     * @return UserDTO, представляющий обновленного пользователя.
     * @throws ResourceNotFoundException если пользователь с данным ID не найден.
     */
    public UserDTO update(UserUpdatedDTO data, Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));
        mapper.update(data, user);
        repository.save(user);
        return mapper.map(user);
    }

    /**
     * Удаляет пользователя по его ID.
     * @param id ID пользователя для удаления.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
