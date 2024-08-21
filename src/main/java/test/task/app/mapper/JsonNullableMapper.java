package test.task.app.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * Mapper для работы с типом {@link JsonNullable}.
 * Этот класс предоставляет методы для обертки и развертки объектов типа
 * {@link JsonNullable}, а также для проверки их наличия. Он используется
 * в процессе маппинга для обработки значений, которые могут быть отсутствующими.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class JsonNullableMapper {
    /**
     * Оборачивает заданный объект в {@link JsonNullable}.
     * @param entity объект, который нужно обернуть.
     * @param <T> тип объекта.
     * @return обернутый объект {@link JsonNullable}.
     */
    public <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    /**
     * Разворачивает объект {@link JsonNullable} в его исходное значение.
     * @param jsonNullable объект, который нужно развернуть.
     * @param <T> тип объекта.
     * @return исходное значение объекта или null, если объект не присутствует.
     */
    public <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    /**
     * Проверяет, присутствует ли значение в {@link JsonNullable}.
     * @param nullable объект, который нужно проверить.
     * @param <T> тип объекта.
     * @return true, если значение присутствует, иначе false.
     */
    @Condition
    public <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }
}
