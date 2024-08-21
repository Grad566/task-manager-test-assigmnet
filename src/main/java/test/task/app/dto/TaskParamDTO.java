package test.task.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для фильтров задач.
 */
@Getter
@Setter
@Schema(description = "Фильтры для задач")
public class TaskParamDTO {
    @Schema(description = "Id назначенного исполнителя")
    private Long assigneeId;
    @Schema(description = "Id автора")
    private Long authorId;
    private String status;
    private String priority;
    @Schema(description = "Часть названия")
    private String titleCont;
}
