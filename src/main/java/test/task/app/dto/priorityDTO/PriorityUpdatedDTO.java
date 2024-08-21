package test.task.app.dto.priorityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO (Data Transfer Object) для обновления приоритета.
 * Этот класс используется для передачи данных об обновлении приоритета.
 * Все поля являются опциональными, что позволяет обновлять только те
 * поля, которые необходимо изменить.
 */
@Getter
@Setter
@Schema(description = "Обновление приоритета, все опционально")
public class PriorityUpdatedDTO {
    @NotNull
    @NotBlank
    @Schema(description = "Название опционально")
    private JsonNullable<String> name;
}
