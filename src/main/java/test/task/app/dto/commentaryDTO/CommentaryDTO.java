package test.task.app.dto.commentaryDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для представления сущности комментария.
 * Этот класс используется для передачи данных о комментарии, включая
 * его идентификатор, содержимое, идентификатор автора, идентификатор задачи
 * и дату создания. Он предназначен для использования в API.
 */
@Getter
@Setter
@Schema(description = "Сущность комментария")
public class CommentaryDTO {
    private Long id;
    private String content;
    private Long authorId;
    private Long taskId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
