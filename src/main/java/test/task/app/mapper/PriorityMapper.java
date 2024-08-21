package test.task.app.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import test.task.app.dto.priorityDTO.PriorityCreatedDTO;
import test.task.app.dto.priorityDTO.PriorityDTO;
import test.task.app.dto.priorityDTO.PriorityUpdatedDTO;
import test.task.app.model.Priority;

/**
 * Mapper для преобразования объектов приоритета между различными представлениями.
 * Этот класс использует MapStruct для автоматического преобразования между
 * {@link PriorityCreatedDTO}, {@link PriorityDTO} и {@link Priority}.
 * Он также определяет стратегию обработки значений null и игнорирует
 * не сопоставленные целевые поля.
 */
@Mapper (
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PriorityMapper {
    /**
     * Преобразует объект {@link PriorityCreatedDTO} в {@link Priority}.
     * @param dto объект, содержащий данные для создания приоритета.
     * @return преобразованный объект {@link Priority}.
     */
    public abstract Priority map(PriorityCreatedDTO dto);

    /**
     * Преобразует объект {@link Priority} в {@link PriorityDTO}.
     * @param model объект приоритета, который нужно преобразовать.
     * @return преобразованный объект {@link PriorityDTO}.
     */
    public abstract PriorityDTO map(Priority model);

    /**
     * Обновляет существующий объект {@link Priority} на основе данных из {@link PriorityUpdatedDTO}.
     * @param dto объект, содержащий данные для обновления приоритета.
     * @param model объект приоритета, который нужно обновить.
     */
    public abstract void update(PriorityUpdatedDTO dto, @MappingTarget Priority model);
}
