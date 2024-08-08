package test.task.app.dto.userDTO;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
public class UserUpdatedDTO {
    private JsonNullable<String> name;
    private JsonNullable<String> email;
    private JsonNullable<String> password;
    private JsonNullable<List<Long>> createdTasks;
    private JsonNullable<List<Long>> assignedTasks;
}
