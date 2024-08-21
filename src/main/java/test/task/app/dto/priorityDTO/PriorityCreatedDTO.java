package test.task.app.dto.priorityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для создания приоритета.
 * Этот класс используется для передачи данных о новом приоритете,
 * который будет создан в системе. Он содержит информацию о названии
 * приоритета, которое является обязательным.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Создание приоритета")
public class PriorityCreatedDTO {
    @NotNull
    @NotBlank
    private String name;
}
