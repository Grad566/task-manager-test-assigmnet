package test.task.app.dto.taskDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

/**
 * DTO (Data Transfer Object) для создания задачи.
 * Этот класс используется для передачи данных о новой задаче,
 * которая будет создана в системе. Он содержит информацию о названии,
 * описании, статусе, приоритете и исполнителях задачи.
 */
@Getter
@Setter
@Schema(description = "Создание задачи")
public class TaskCreatedDTO {
    @NotNull
    @Schema(description = "Название обязательно")
    private String title;
    @Schema(description = "Описание опционально")
    private JsonNullable<String> description;
    @NotNull
    @Schema(description = "Статус обязательно")
    private String status;
    @Schema(description = "Приоритет опционально")
    private JsonNullable<String> priority;
    @Schema(description = "Исполнители опционально")
    private JsonNullable<List<Long>> assigneeIds;
}
