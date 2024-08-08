package test.task.app.dto.taskDTO;



import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
public class TaskUpdatedDTO {
    private JsonNullable<String> title;
    private JsonNullable<String> description;
    private JsonNullable<String> status;
    private JsonNullable<String> priority;
    private JsonNullable<List<Long>> assigneeIds;
}
