package test.task.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import test.task.app.dto.taskStatusDTO.TaskStatusCreatedDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusUpdatedDTO;
import test.task.app.service.TaskStatusService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/task_statuses")
@RequiredArgsConstructor
public class TaskStatusController {
    private final TaskStatusService service;

    @GetMapping(path = "/{id}")
    public TaskStatusDTO getById(@PathVariable Long id) {
        return service.show(id);
    }

    @GetMapping()
    public List<TaskStatusDTO> getAll() {
        return service.getAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO create(@Valid @RequestBody TaskStatusCreatedDTO data) {
        return service.create(data);
    }

    @PutMapping(path = "/{id}")
    public TaskStatusDTO update(@Valid @RequestBody TaskStatusUpdatedDTO data, @PathVariable Long id) {
        return service.update(data, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
