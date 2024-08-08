package test.task.app.dto.priorityDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PriorityUpdatedDTO {
    @NotNull
    @NotBlank
    private JsonNullable<String> name;
}
