package test.task.app.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import test.task.app.model.BaseEntity;

/**
 * Mapper для преобразования идентификаторов в сущности.
 * Этот класс предоставляет методы для получения сущностей из базы данных
 * по их идентификаторам с использованием {@link EntityManager}. Он используется
 * для преобразования идентификаторов в соответствующие объекты сущностей.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;

    /**
     * Находит сущность по заданному идентификатору.
     * @param id идентификатор сущности, которую нужно найти.
     * @param entityClass класс сущности, которую нужно вернуть.
     * @param <T> тип сущности, который расширяет {@link BaseEntity}.
     * @return найденная сущность или null, если идентификатор равен null.
     */
    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> entityClass) {
        return id != null ? entityManager.find(entityClass, id) : null;
    }
}
