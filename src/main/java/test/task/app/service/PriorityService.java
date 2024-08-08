package test.task.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.task.app.dto.priorityDTO.PriorityCreatedDTO;
import test.task.app.dto.priorityDTO.PriorityDTO;
import test.task.app.dto.priorityDTO.PriorityUpdatedDTO;
import test.task.app.exception.ResourceNotFoundException;
import test.task.app.mapper.PriorityMapper;
import test.task.app.repository.PriorityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriorityService {
    private final PriorityRepository repository;
    private final PriorityMapper mapper;

    public List<PriorityDTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    public PriorityDTO show(Long id) {
        var priority = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority with id " + id + " not found!"));
        return mapper.map(priority);
    }

    public PriorityDTO create(PriorityCreatedDTO data) {
        var priority = mapper.map(data);
        repository.save(priority);
        return mapper.map(priority);
    }

    public PriorityDTO update(PriorityUpdatedDTO data, Long id) {
        var priority = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority with id " + id + " not found!"));
        mapper.update(data, priority);
        repository.save(priority);
        return mapper.map(priority);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
