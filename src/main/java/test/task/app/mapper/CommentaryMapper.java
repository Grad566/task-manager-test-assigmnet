package test.task.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import test.task.app.dto.commentaryDTO.CommentaryCreatedDTO;
import test.task.app.dto.commentaryDTO.CommentaryDTO;
import test.task.app.dto.commentaryDTO.CommentaryUpdatedDTO;
import test.task.app.model.Commentary;

/**
 * Mapper для преобразования объектов комментариев между различными представлениями.
 * Этот класс использует MapStruct для автоматического преобразования между
 * {@link CommentaryCreatedDTO}, {@link CommentaryDTO} и {@link Commentary}.
 * Он также определяет стратегию обработки значений null и игнорирует
 * не сопоставленные целевые поля.
 */
@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CommentaryMapper {
    /**
     * Преобразует объект {@link CommentaryCreatedDTO} в {@link Commentary}.
     * @param data объект, содержащий данные для создания комментария.
     * @return преобразованный объект {@link Commentary}.
     */
    @Mapping(target = "task", source = "taskId")
    public abstract Commentary map(CommentaryCreatedDTO data);

    /**
     * Преобразует объект {@link Commentary} в {@link CommentaryDTO}.
     * @param model объект комментария, который нужно преобразовать.
     * @return преобразованный объект {@link CommentaryDTO}.
     */
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "authorId", source = "author.id")
    public abstract CommentaryDTO map(Commentary model);

    /**
     * Обновляет существующий объект {@link Commentary} на основе данных из {@link CommentaryUpdatedDTO}.
     * @param data объект, содержащий данные для обновления комментария.
     * @param model объект комментария, который нужно обновить.
     */
    public abstract void update(CommentaryUpdatedDTO data, @MappingTarget Commentary model);
}
