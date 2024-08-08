package test.task.app.dto.taskStatusDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
@Schema(description = "Обновление статуса")
public class TaskStatusUpdatedDTO {
    @Schema(description = "Название опционально")
    private JsonNullable<String> name;
}
