package test.task.app.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import test.task.app.dto.userDTO.UserCreatedDTO;
import test.task.app.dto.userDTO.UserDTO;
import test.task.app.dto.userDTO.UserUpdatedDTO;
import test.task.app.model.Task;
import test.task.app.model.User;
import test.task.app.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper для преобразования объектов пользователя между различными представлениями
 * Этот класс использует MapStruct для автоматического преобразования между
 * {@link UserCreatedDTO}, {@link UserDTO} и {@link User}.
 *
 * Методы не относящиеся к map и update, используя самим маппером.
 */
@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Преобразует объект {@link UserCreatedDTO} в {@link User}.
     * @param dto объект, содержащий данные для создания пользователя.
     * @return преобразованный объект {@link User}.
     */
    public abstract User map(UserCreatedDTO dto);

    /**
     * Преобразует объект {@link User} в {@link UserDTO}.
     * @param model объект пользователя, который нужно преобразовать.
     * @return преобразованный объект {@link UserDTO}.
     */
    public abstract UserDTO map(User model);

    /**
     * Обновляет существующий объект {@link User} на основе данных из {@link UserUpdatedDTO}.
     * @param dto объект, содержащий данные для обновления пользователя.
     * @param model объект пользователя, который нужно обновить.
     */
    public abstract void update(UserUpdatedDTO dto, @MappingTarget User model);

    /**
     * Преобразует список объектов {@link Task} в список их идентификаторов.
     * @param tasks список задач.
     * @return список идентификаторов задач.
     */
    public List<Long> toTaskIds(List<Task> tasks) {
        if (tasks == null) {
            return new ArrayList<Long>();
        } else {
            return tasks.stream().map(Task::getId).toList();
        }
    }

    /**
     * Преобразует список идентификаторов задач в список объектов {@link Task}.
     * @param tasksIds список идентификаторов задач.
     * @return список объектов {@link Task}.
     */
    public List<Task> toTasks(List<Long> tasksIds) {
        if (tasksIds == null) {
            return new ArrayList<Task>();
        } else {
            return tasksIds.stream().map(i -> taskRepository.findById(i).orElseThrow()).toList();
        }
    }

    /**
     *  Шифрует пароль пользователя перед маппингом из {@link UserCreatedDTO}.
     * @param dto объект, содержащий данные для создания пользователя.
     */
    @BeforeMapping
    public void encryptPassword(UserCreatedDTO dto) {
        var password = dto.getPassword();
        dto.setPassword(encoder.encode(password));
    }

    /**
     * Шифрует пароль пользователя перед обновлением из {@link UserUpdatedDTO}.
     * @param dto dto объект, содержащий данные для обновления пользователя.
     * @param model model объект пользователя, который нужно обновить.
     */
    @BeforeMapping
    public void encryptPassword(UserUpdatedDTO dto, @MappingTarget User model) {
        if (dto.getPassword() != null && dto.getPassword().isPresent()) {
            String password = dto.getPassword().get();
            model.setPassword(encoder.encode(password));
        }
    }

}
