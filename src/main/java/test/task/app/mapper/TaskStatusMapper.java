package test.task.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import test.task.app.dto.taskStatusDTO.TaskStatusCreatedDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusUpdatedDTO;
import test.task.app.model.TaskStatus;

/**
 * Mapper для преобразования объектов статуса задачи между различными представлениями.
 * Этот класс использует MapStruct для автоматического преобразования между
 * {@link TaskStatusCreatedDTO}, {@link TaskStatusDTO} и {@link TaskStatus}.
 * Он также определяет стратегию обработки значений null и игнорирует
 * не сопоставленные целевые поля.
 */
@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskStatusMapper {
    /**
     * Преобразует объект {@link TaskStatus} в {@link TaskStatusDTO}.
     * @param model объект статуса задачи, который нужно преобразовать.
     * @return преобразованный объект {@link TaskStatusDTO}.
     */
    public abstract TaskStatusDTO map(TaskStatus model);

    /**
     * Преобразует объект {@link TaskStatusCreatedDTO} в {@link TaskStatus}.
     * @param dto объект, содержащий данные для создания статуса задачи.
     * @return преобразованный объект {@link TaskStatus}.
     */
    public abstract TaskStatus map(TaskStatusCreatedDTO dto);

    /**
     * Обновляет существующий объект {@link TaskStatus} на основе данных из {@link TaskStatusUpdatedDTO}.
     * @param dto объект, содержащий данные для обновления статуса задачи.
     * @param model объект статуса задачи, который нужно обновить.
     */
    public abstract void update(TaskStatusUpdatedDTO dto, @MappingTarget TaskStatus model);
}
