package test.task.app.dto.commentaryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Создание комментария")
public class CommentaryCreatedDTO {
    @NotBlank
    private String content;
    @NotNull
    private Long taskId;
}
