package test.task.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, когда запрашиваемый ресурс не найден.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Конструктор для создания исключения с заданным сообщением.
     * @param message Сообщение, описывающее причину исключения.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
