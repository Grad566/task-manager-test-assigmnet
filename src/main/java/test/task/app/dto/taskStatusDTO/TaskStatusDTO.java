package test.task.app.dto.taskStatusDTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO для представления сущности статуса задачи.
 *
 */
@Getter
@Setter
@Schema(description = "Сущность статуса")
public class TaskStatusDTO {
    private Long id;
    private String name;
    private LocalDate createdAt;
}
