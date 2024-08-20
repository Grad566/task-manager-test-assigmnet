package test.task.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.task.app.dto.taskStatusDTO.TaskStatusCreatedDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusUpdatedDTO;
import test.task.app.exception.ResourceNotFoundException;
import test.task.app.mapper.TaskStatusMapper;
import test.task.app.repository.TaskStatusRepository;

import java.util.List;

/**
 * Сервисный класс для управления статусами задач.
 * Этот класс предоставляет методы для выполнения операций CRUD над статусами задач.
 */
@Service
@RequiredArgsConstructor
public class TaskStatusService {
    private final TaskStatusRepository repository;
    private final TaskStatusMapper mapper;

    /**
     * Получает все статусы задач.
     * @return список TaskStatusDTO, представляющий все статусы задач.
     */
    public List<TaskStatusDTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    /**
     * Получает конкретный статус задачи по его ID.
     * @param id статуса задачи для получения.
     * @return TaskStatusDTO, представляющий статус задачи.
     * @throws ResourceNotFoundException если статус задачи с данным ID не найден.
     */
    public TaskStatusDTO show(Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id " + id + " not found!"));
        return mapper.map(taskStatus);
    }

    /**
     * Создает новый статус задачи.
     * @param data объект передачи данных, содержащий информацию для нового статуса задачи.
     * @return TaskStatusDTO, представляющий созданный статус задачи.
     */
    public TaskStatusDTO create(TaskStatusCreatedDTO data) {
        var taskStatus = mapper.map(data);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    /**
     * Обновляет существующий статус задачи.
     * @param data объект передачи данных, содержащий обновленную информацию для статуса задачи.
     * @param id статуса задачи для обновления.
     * @return TaskStatusDTO, представляющий обновленный статус задачи.
     * @throws ResourceNotFoundException если статус задачи с данным ID не найден.
     */
    public TaskStatusDTO update(TaskStatusUpdatedDTO data, Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id " + id + " not found!"));
        mapper.update(data, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    /**
     * Удаляет статус задачи по его ID.
     * @param id статуса задачи для удаления.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
