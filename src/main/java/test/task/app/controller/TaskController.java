package test.task.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import test.task.app.dto.TaskParamDTO;
import test.task.app.dto.taskDTO.TaskCreatedDTO;
import test.task.app.dto.taskDTO.TaskDTO;
import test.task.app.dto.taskDTO.TaskUpdatedDTO;
import test.task.app.service.TaskService;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Контроллер для управления задачами.
 * Этот контроллер обрабатывает запросы, связанные с задачами CRUD + обновление статуса.
 */
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    /**
     * Получает задачу по ее ID.
     * @param id ID задачи для получения.
     * @return объект {@link TaskDTO}, представляющий задачу.
     */
    @GetMapping(path = "/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        return service.show(id);
    }

    /**
     * Получает все задачи с поддержкой пагинации и фильтрации.
     * @param page номер страницы (по умолчанию 0)
     * @param size количество задач на странице (по умолчанию 100).
     * @param params параметры для фильтрации задач.
     * @return список {@link TaskDTO}, представляющий все задачи.
     */
    @GetMapping()
    public List<TaskDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            TaskParamDTO params
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable, params);
    }

    /**
     * Создает новую задачу.
     * @param data объект {@link TaskCreatedDTO}, содержащий данные для создания задачи.
     * @return объект {@link TaskDTO}, представляющий созданную задачу.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreatedDTO data) {
        return service.create(data);
    }

    /**
     * Обновляет существующую задачу.
     * @param data объект {@link TaskUpdatedDTO}, содержащий обновленные данные задачи.
     * @param id ID задачи для обновления.
     * @return объект {@link TaskDTO}, представляющий обновленную задачу.
     */
    @PreAuthorize("@taskRepository.findById(#id).get().getAuthor().getEmail() == authentication.name")
    @PutMapping(path = "/{id}")
    public TaskDTO update(@Valid @RequestBody TaskUpdatedDTO data, @PathVariable Long id) {
        return service.update(data, id);
    }

    /**
     * Обновляет статус существующей задачи.
     * @param taskStatusId ID нового статуса задачи.
     * @param id ID задачи для обновления статуса.
     * @return объект {@link TaskDTO}, представляющий задачу с обновленным статусом.
     */
    @PreAuthorize("@taskRepository.findById(#id).get().getAssignees().stream()"
            + ".anyMatch(u -> u.getEmail() == authentication.name)")
    @PutMapping(path = "/{id}/status")
    public TaskDTO updateStatus(Long taskStatusId, @PathVariable Long id) {
        return service.updateStatus(taskStatusId, id);
    }

    /**
     * Удаляет задачу по ее ID.
     * @param id ID задачи для удаления.
     */
    @PreAuthorize("@taskRepository.findById(#id).get().getAuthor().getEmail() == authentication.name")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
