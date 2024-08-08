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

@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping(path = "/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        return service.show(id);
    }

    @GetMapping()
    public List<TaskDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            TaskParamDTO params
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable, params);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreatedDTO data) {
        return service.create(data);
    }

    @PreAuthorize("@taskRepository.findById(#id).get().getAuthor().getEmail() == authentication.name")
    @PutMapping(path = "/{id}")
    public TaskDTO update(@Valid @RequestBody TaskUpdatedDTO data, @PathVariable Long id) {
        return service.update(data, id);
    }

    @PreAuthorize("@taskRepository.findById(#id).get().getAssignees().stream()"
            + ".anyMatch(u -> u.getEmail() == authentication.name)")
    @PutMapping(path = "/{id}/status")
    public TaskDTO updateStatus(Long taskStatusId, @PathVariable Long id) {
        return service.updateStatus(taskStatusId, id);
    }

    @PreAuthorize("@taskRepository.findById(#id).get().getAuthor().getEmail() == authentication.name")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
