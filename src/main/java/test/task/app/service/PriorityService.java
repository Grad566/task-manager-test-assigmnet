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

/**
 * Сервис для управления приоритетами.
 * Предоставляет методы для CRUD приоритетов.
 */
@Service
@RequiredArgsConstructor
public class PriorityService {
    private final PriorityRepository repository;
    private final PriorityMapper mapper;

    /**
     * Получает список всех приоритетов.
     * Список DTO всех приоритетов.
     * @return
     */
    public List<PriorityDTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    /**
     * Показывает приоритет по его идентификатору.
     * @param id Идентификатор приоритета.
     * @return DTO приоритета.
     * @throws ResourceNotFoundException Если приоритет с указанным идентификатором не найден.
     */
    public PriorityDTO show(Long id) {
        var priority = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority with id " + id + " not found!"));
        return mapper.map(priority);
    }

    /**
     * Создает новый приоритет.
     * @param data Данные для создания приоритета.
     * @return DTO созданного приоритета.
     */
    public PriorityDTO create(PriorityCreatedDTO data) {
        var priority = mapper.map(data);
        repository.save(priority);
        return mapper.map(priority);
    }

    /**
     * Обновляет существующий приоритет.
     * @param data Данные для обновления приоритета.
     * @param id Идентификатор приоритета, который нужно обновить.
     * @return DTO обновленного приоритета.
     * @throws ResourceNotFoundException Если приоритет с указанным идентификатором не найден.
     */
    public PriorityDTO update(PriorityUpdatedDTO data, Long id) {
        var priority = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority with id " + id + " not found!"));
        mapper.update(data, priority);
        repository.save(priority);
        return mapper.map(priority);
    }

    /**
     * @param id
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
