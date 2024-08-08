package test.task.app.service;

import lombok.RequiredArgsConstructor;
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
import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final TaskStatusRepository taskStatusRepository;
    private final UserUtils userUtils;
    private final TaskSpecification taskSpecification;

    public List<TaskDTO> getAll(Pageable pageable, TaskParamDTO params) {
        var spec = taskSpecification.build(params);
        return repository.findAll(spec, pageable).stream().map(mapper::map).toList();
    }

    public TaskDTO show(Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found!"));
        return mapper.map(task);
    }

    public TaskDTO create(TaskCreatedDTO data) {
        Task task = mapper.map(data);
        task.setAuthor(userUtils.getCurrentUser());
        repository.save(task);
        return mapper.map(task);
    }

    public TaskDTO update(TaskUpdatedDTO data, Long id) {
        var task = repository.findById(id).orElseThrow();
        mapper.update(data, task);
        repository.save(task);
        return mapper.map(task);
    }

    public TaskDTO updateStatus(Long taskStatusId, Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found!"));
        task.setStatus(taskStatusRepository.findById(taskStatusId).orElseThrow());
        repository.save(task);
        return mapper.map(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
