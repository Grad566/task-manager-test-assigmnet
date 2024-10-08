package test.task.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для запроса аутентификации пользователя.
 */
@Getter
@Setter
public class AuthRequest {
    @Schema(description = "Почта")
    private String username;
    private String password;
}
