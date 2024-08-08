package test.task.app.dto.userDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
@Schema(description = "Создание пользователя")
public class UserCreatedDTO {
    @Schema(description = "Имя опционально")
    private JsonNullable<String> name;
    @NotNull
    @Email
    @Schema(description = "Почта обязательно")
    private String email;
    @NotNull
    @Size(min = 3)
    @Schema(description = "Пароль обязательно")
    private String password;
}
