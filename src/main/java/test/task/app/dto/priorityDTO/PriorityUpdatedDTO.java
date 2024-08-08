package test.task.app.dto.priorityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
@Schema(description = "Обновление приоритета")
public class PriorityUpdatedDTO {
    @NotNull
    @NotBlank
    @Schema(description = "Название опционально")
    private JsonNullable<String> name;
}
