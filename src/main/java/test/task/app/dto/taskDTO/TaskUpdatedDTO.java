package test.task.app.dto.taskDTO;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
@Schema(description = "Обновление задачи, все опционально")
public class TaskUpdatedDTO {
    @Schema(description = "Название опционально")
    private JsonNullable<String> title;
    @Schema(description = "Описание опционально")
    private JsonNullable<String> description;
    @Schema(description = "Статус опционально")
    private JsonNullable<String> status;
    @Schema(description = "Приоритет опционально")
    private JsonNullable<String> priority;
    @Schema(description = "Исполнители опционально")
    private JsonNullable<List<Long>> assigneeIds;
}
