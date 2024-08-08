package test.task.app.dto.taskStatusDTO;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskStatusUpdatedDTO {
    private JsonNullable<String> name;
}
