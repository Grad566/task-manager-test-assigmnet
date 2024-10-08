package test.task.app.dto.taskDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO (Data Transfer Object) для представления сущности задачи.
 * Этот класс используется для передачи данных о задаче, включая
 * её название, описание, статус, приоритет, идентификатор автора,
 * исполнителей и комментарии. Он предназначен для использования в API.
 */
@Getter
@Setter
@Schema(description = "Сущность задача")
public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private String priority;
    private String authorId;
    private List<Long> assigneeIds;
    private List<Long> commentariesIds;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
