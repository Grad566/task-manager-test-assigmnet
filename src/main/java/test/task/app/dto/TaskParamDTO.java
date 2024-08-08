package test.task.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskParamDTO {
    private Long assigneeId;
    private Long authorId;
    private String status;
    private String priority;
    private String titleCont;
}
