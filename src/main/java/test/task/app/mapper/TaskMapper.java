package test.task.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import test.task.app.dto.taskDTO.TaskCreatedDTO;
import test.task.app.dto.taskDTO.TaskDTO;
import test.task.app.dto.taskDTO.TaskUpdatedDTO;
import test.task.app.exception.ResourceNotFoundException;
import test.task.app.model.Commentary;
import test.task.app.model.Priority;
import test.task.app.model.Task;
import test.task.app.model.TaskStatus;
import test.task.app.model.User;
import test.task.app.repository.CommentaryRepository;
import test.task.app.repository.PriorityRepository;
import test.task.app.repository.TaskStatusRepository;
import test.task.app.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper для преобразования объектов задач между различными представлениями.
 * Этот класс использует MapStruct для автоматического преобразования между
 * {@link TaskCreatedDTO}, {@link TaskDTO} и {@link Task}. Он также определяет
 * методы для преобразования идентификаторов исполнителей, приоритетов, статусов задач
 * и комментариев. Все операции выполняются с использованием соответствующих репозиториев.
 *
 * Методы не относящиеся к map и update, используя самим маппером.
 */
@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private CommentaryRepository commentaryRepository;

    /**
     * Преобразует объект {@link TaskCreatedDTO} в {@link Task}.
     * @param dto объект, содержащий данные для создания задачи.
     * @return преобразованный объект {@link Task}.
     */
    @Mapping(target = "assignees", source = "assigneeIds")
    public abstract Task map(TaskCreatedDTO dto);

    /**
     * Преобразует объект {@link Task} в {@link TaskDTO}.
     * @param model объект задачи, который нужно преобразовать.
     * @return преобразованный объект {@link TaskDTO}.
     */
    @Mapping(target = "status", source = "status.name")
    @Mapping(target = "priority", source = "priority.name")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assigneeIds", source = "assignees")
    @Mapping(target = "commentariesIds", source = "commentaries")
    public abstract TaskDTO map(Task model);

    /**
     * Обновляет существующий объект {@link Task} на основе данных из {@link TaskUpdatedDTO}.
     * @param dto объект, содержащий данные для обновления задачи.
     * @param model объект задачи, который нужно обновить.
     */
    @Mapping(target = "assignees", source = "assigneeIds")
    public abstract void update(TaskUpdatedDTO dto, @MappingTarget Task model);

    /**
     * Преобразует список идентификаторов исполнителей в список объектов {@link User}.
     * @param ids список идентификаторов исполнителей.
     * @return список объектов {@link User}.
     */
    public List<User> toAssignees(List<Long> ids) {
        return new ArrayList<>(userRepository.findAllById(ids));
    }

    /**
     * Находит объект {@link Priority} по его имени.
     * @param priority имя приоритета.
     * @return объект {@link Priority}.
     * @throws ResourceNotFoundException если приоритет не найден.
     */
    public Priority toPriority(String priority) {
        return priorityRepository.findByName(priority)
                .orElseThrow(() -> new ResourceNotFoundException("priority +" + priority + " not found!"));
    }

    /**
     * Находит объект {@link TaskStatus} по его имени.
     * @param status имя статуса задачи.
     * @return объект {@link TaskStatus}.
     * @throws ResourceNotFoundException если статус не найден.
     */
    public TaskStatus toTaskStatus(String status) {
        return taskStatusRepository.findByName(status)
                .orElseThrow(() -> new ResourceNotFoundException("status +" + status + " not found!"));
    }

    /**
     * Преобразует список объектов {@link Commentary} в список их идентификаторов.
     * @param comments comments список комментариев.
     * @return список идентификаторов комментариев.
     */
    public List<Long> toCommentariesIds(List<Commentary> comments) {
        if (comments == null) {
            return new ArrayList<>();
        } else {
            return comments.stream().map(Commentary::getId).toList();
        }
    }

    /**
     * Преобразует список объектов {@link User} в список их идентификаторов.
     * @param users список пользователей.
     * @return список идентификаторов пользователей.
     */
    public List<Long> toAssigneesIds(List<User> users) {
        if (users == null) {
            return new ArrayList<Long>();
        } else {
            return users.stream().map(User::getId).toList();
        }
    }
}
