package test.task.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import test.task.app.dto.TaskParamDTO;
import test.task.app.exception.ResourceNotFoundException;
import test.task.app.mapper.TaskMapper;
import test.task.app.model.Task;
import test.task.app.repository.TaskRepository;
import test.task.app.dto.taskDTO.TaskDTO;
import test.task.app.dto.taskDTO.TaskCreatedDTO;
import test.task.app.dto.taskDTO.TaskUpdatedDTO;
import test.task.app.repository.TaskStatusRepository;
import test.task.app.specification.TaskSpecification;
import test.task.app.util.UserUtils;

import org.springframework.data.domain.Pageable;

/**
 * Сервис для управления задачами.
 * Предоставляет методы CRUD задач.
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final TaskStatusRepository taskStatusRepository;
    private final UserUtils userUtils;
    private final TaskSpecification taskSpecification;

    /**
     * Получает список всех задач с учетом параметров и пагинации.
     * @param pageable Параметры пагинации.
     * @param params Параметры фильтрации задач.
     * @return {@link Page<TaskDTO>} объект, содержащий список задач и метаинформацию о пагинации.
     */
    public Page<TaskDTO> getAll(Pageable pageable, TaskParamDTO params) {
        var spec = taskSpecification.build(params);
        return repository.findAll(spec, pageable).map(mapper::map);
    }

    /**
     * Показывает задачу по ее идентификатору.
     * @param id Идентификатор задачи.
     * @return DTO задачи.
     * @throws ResourceNotFoundException Если задача с указанным идентификатором не найдена.
     */
    public TaskDTO show(Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found!"));
        return mapper.map(task);
    }

    /**
     * Создает новую задачу.
     * @param data Данные для создания задачи.
     * @return DTO созданной задачи.
     */
    public TaskDTO create(TaskCreatedDTO data) {
        Task task = mapper.map(data);
        task.setAuthor(userUtils.getCurrentUser());
        repository.save(task);
        return mapper.map(task);
    }

    /**
     * Обновляет существующую задачу.
     * @param data Данные для обновления задачи.
     * @param id Идентификатор задачи, которую нужно обновить.
     * @return DTO обновленной задачи.
     * @throws ResourceNotFoundException Если задача с указанным идентификатором не найдена.
     */
    public TaskDTO update(TaskUpdatedDTO data, Long id) {
        var task = repository.findById(id).orElseThrow();
        mapper.update(data, task);
        repository.save(task);
        return mapper.map(task);
    }

    /**
     * Обновляет статус задачи.
     * @param taskStatusId Идентификатор нового статуса задачи.
     * @param id Идентификатор задачи, статус которой нужно обновить.
     * @return DTO обновленной задачи.
     * @throws ResourceNotFoundException Если задача с указанным идентификатором или статус не найдены.
     */
    public TaskDTO updateStatus(Long taskStatusId, Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found!"));
        task.setStatus(taskStatusRepository.findById(taskStatusId).orElseThrow());
        repository.save(task);
        return mapper.map(task);
    }

    /**
     * Удаляет задачу по ее идентификатору.
     * @param id Идентификатор задачи, которую нужно удалить.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
