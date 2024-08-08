package test.task.app.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
public class UserCreatedDTO {
    private JsonNullable<String> name;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 3)
    private String password;
    private JsonNullable<List<Long>> createdTasks;
    private JsonNullable<List<Long>> assignedTasks;
}
