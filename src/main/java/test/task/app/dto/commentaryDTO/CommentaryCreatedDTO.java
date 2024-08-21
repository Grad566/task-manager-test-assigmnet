package test.task.app.dto.commentaryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для создания комментария.
 * Этот класс используется для передачи данных о комментарии, который
 * будет создан в системе. Он содержит информацию о содержимом комментария
 * и идентификаторе задачи, к которой этот комментарий относится.
 */
@Setter
@Getter
@Schema(description = "Создание комментария")
public class CommentaryCreatedDTO {
    @NotBlank
    private String content;
    @NotNull
    private Long taskId;
}
