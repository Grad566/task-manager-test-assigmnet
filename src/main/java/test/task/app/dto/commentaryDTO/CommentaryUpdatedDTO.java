package test.task.app.dto.commentaryDTO;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class CommentaryUpdatedDTO {
    private JsonNullable<String> content;
}
