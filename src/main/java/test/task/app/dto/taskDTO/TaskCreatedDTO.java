package test.task.app.dto.taskDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
public class TaskCreatedDTO {
    @NotNull
    private String title;
    private JsonNullable<String> description;
    @NotNull
    private String status;
    private JsonNullable<String> priority;
    private JsonNullable<List<Long>> assigneeIds;
}
