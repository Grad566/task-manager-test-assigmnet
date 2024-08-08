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
import test.task.app.dto.priorityDTO.PriorityCreatedDTO;
import test.task.app.dto.priorityDTO.PriorityDTO;
import test.task.app.dto.priorityDTO.PriorityUpdatedDTO;
import test.task.app.service.PriorityService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/priorities")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService service;

    @GetMapping(path = "/{id}")
    public PriorityDTO getById(@PathVariable Long id) {
        return service.show(id);
    }

    @GetMapping()
    public List<PriorityDTO> getAll() {
        return service.getAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PriorityDTO create(@Valid @RequestBody PriorityCreatedDTO data) {
        return service.create(data);
    }

    @PutMapping(path = "/{id}")
    public PriorityDTO update(@Valid @RequestBody PriorityUpdatedDTO data, @PathVariable Long id) {
        return service.update(data, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
