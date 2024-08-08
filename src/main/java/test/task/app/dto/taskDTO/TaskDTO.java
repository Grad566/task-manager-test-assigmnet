package test.task.app.dto.taskDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private String priority;
    private String authorId;
    private List<Long> assigneeIds;
    private List<Long> commentariesIds;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
