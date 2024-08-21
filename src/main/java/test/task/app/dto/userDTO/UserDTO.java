package test.task.app.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO для представления сущности пользователя.
 */
@Getter
@Setter
@Schema(description = "Сущность пользователя")
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @Schema(description = "Id созданных задач")
    private List<Long> createdTasks;
    @Schema(description = "Id назначенных задач")
    private List<Long> assignedTasks;
}
