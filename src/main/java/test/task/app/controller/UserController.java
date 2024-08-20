package test.task.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import test.task.app.dto.userDTO.UserCreatedDTO;
import test.task.app.dto.userDTO.UserDTO;
import test.task.app.dto.userDTO.UserUpdatedDTO;
import test.task.app.service.UserService;

import java.util.List;

/**
 * Контроллер для управления пользователями.
 * Этот контроллер обрабатывает запросы, связанные с пользователями CRUD.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService service;

    /**
     * Получает пользователя по его ID.
     * @param id ID пользователя для получения.
     * @return объект {@link UserDTO}, представляющий пользователя.
     */
    @GetMapping(path = "/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return service.show(id);
    }

    /**
     * Получает всех пользователей.
     * @return список {@link UserDTO}, представляющий всех пользователей.
     */
    @GetMapping()
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    /**
     * Создает нового пользователя.
     * @param data объект {@link UserCreatedDTO}, содержащий данные для создания пользователя.
     * @return объект {@link UserDTO}, представляющий созданного пользователя.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreatedDTO data) {
        return service.create(data);
    }

    /**
     * Обновляет существующего пользователя.
     * @param data объект {@link UserUpdatedDTO}, содержащий обновленные данные пользователя.
     * @param id ID пользователя для обновления.
     * @return объект {@link UserDTO}, представляющий обновленного пользователя.
     */
    @PreAuthorize("@userRepository.findById(#id).get().getEmail() == authentication.name")
    @PutMapping(path = "/{id}")
    public UserDTO update(@Valid @RequestBody UserUpdatedDTO data, @PathVariable Long id) {
        return service.update(data, id);
    }

    /**
     * Удаляет пользователя по его ID
     * @param id ID пользователя для удаления.
     */
    @PreAuthorize("@userRepository.findById(#id).get().getEmail() == authentication.name")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
