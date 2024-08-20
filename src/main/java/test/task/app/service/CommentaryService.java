package test.task.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.task.app.dto.commentaryDTO.CommentaryCreatedDTO;
import test.task.app.dto.commentaryDTO.CommentaryDTO;
import test.task.app.dto.commentaryDTO.CommentaryUpdatedDTO;

import test.task.app.exception.ResourceNotFoundException;
import test.task.app.mapper.CommentaryMapper;
import test.task.app.repository.CommentaryRepository;
import test.task.app.util.UserUtils;

import java.util.List;

/**
 * Сервис для управления комментариями.
 * Предоставляет методы для CRUD комментариев.
 */
@Service
@RequiredArgsConstructor
public class CommentaryService {
    private final CommentaryRepository repository;
    private final CommentaryMapper mapper;
    private final UserUtils userUtils;

    /**
     * Получает список всех комментариев.
     * @return Список DTO всех комментариев.
     */
    public List<CommentaryDTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    /**
     * Показывает комментарий по его идентификатору.
     * @param id Идентификатор комментария.
     * @return DTO комментария.
     * @throws ResourceNotFoundException Если комментарий с указанным идентификатором не найден.
     */
    public CommentaryDTO show(Long id) {
        var commentary = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found!"));
        return mapper.map(commentary);
    }

    /**
     * Создает новый комментарий.
     * @param data Данные для создания комментария.
     * @return DTO созданного комментария.
     */
    public CommentaryDTO create(CommentaryCreatedDTO data) {
        var commentary = mapper.map(data);
        commentary.setAuthor(userUtils.getCurrentUser());
        repository.save(commentary);
        return mapper.map(commentary);
    }

    /**
     * Обновляет существующий комментарий.
     * @param data Данные для обновления комментария.
     * @param id Идентификатор комментария, который нужно обновить.
     * @return DTO обновленного комментария.
     * @throws ResourceNotFoundException Если комментарий с указанным идентификатором не найден.
     */
    public CommentaryDTO update(CommentaryUpdatedDTO data, Long id) {
        var commentary = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found!"));
        mapper.update(data, commentary);
        repository.save(commentary);
        return mapper.map(commentary);
    }

    /**
     * Удаляет приоритет по его идентификатору
     * @param id Идентификатор приоритета, который нужно удалить.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
