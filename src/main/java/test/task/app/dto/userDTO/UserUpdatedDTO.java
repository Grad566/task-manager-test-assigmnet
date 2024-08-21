package test.task.app.dto.userDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO для обновления пользователя. Все поля являются опциональными.
 */
@Getter
@Setter
@Schema(description = "Обновление пользователя, все опционально")
public class UserUpdatedDTO {
    private JsonNullable<String> name;
    private JsonNullable<String> email;
    private JsonNullable<String> password;
}
