package test.task.app.dto.commentaryDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentaryCreatedDTO {
    @NotBlank
    private String content;
    @NotNull
    private Long taskId;
}
