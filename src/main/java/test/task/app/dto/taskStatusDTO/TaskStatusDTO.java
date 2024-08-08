package test.task.app.dto.taskStatusDTO;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskStatusDTO {
    private Long id;
    private String name;
    private LocalDate createdAt;
}
