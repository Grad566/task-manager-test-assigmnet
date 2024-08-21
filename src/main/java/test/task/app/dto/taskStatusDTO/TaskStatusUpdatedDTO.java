package test.task.app.dto.taskStatusDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO для обновления статуса задачи. Все поля являются опциональными.
 */
@Getter
@Setter
@Schema(description = "Обновление статуса, все опционально")
public class TaskStatusUpdatedDTO {
    @Schema(description = "Название опционально")
    private JsonNullable<String> name;
}
