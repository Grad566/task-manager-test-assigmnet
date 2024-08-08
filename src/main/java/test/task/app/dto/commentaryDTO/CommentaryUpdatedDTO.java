package test.task.app.dto.commentaryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
@Schema(description = "обновления комментария")
public class CommentaryUpdatedDTO {
    @Schema(description = "Содержание опционально")
    private JsonNullable<String> content;
}
