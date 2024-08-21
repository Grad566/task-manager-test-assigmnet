package test.task.app.dto.commentaryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO (Data Transfer Object) для обновления комментария.
 * Этот класс используется для передачи данных об обновлении комментария.
 * Все поля являются опциональными, что позволяет обновлять только те
 * поля, которые необходимо изменить.
 */
@Getter
@Setter
@Schema(description = "обновления комментария, все опционально")
public class CommentaryUpdatedDTO {
    @Schema(description = "Содержание опционально")
    private JsonNullable<String> content;
}
