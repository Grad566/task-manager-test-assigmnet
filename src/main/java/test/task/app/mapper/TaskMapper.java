package test.task.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import test.task.app.dto.taskDTO.TaskCreatedDTO;
import test.task.app.dto.taskDTO.TaskDTO;
import test.task.app.dto.taskDTO.TaskUpdatedDTO;
import test.task.app.exception.ResourceNotFoundException;
import test.task.app.model.Commentary;
import test.task.app.model.Priority;
import test.task.app.model.Task;
import test.task.app.model.TaskStatus;
import test.task.app.model.User;
import test.task.app.repository.CommentaryRepository;
import test.task.app.repository.PriorityRepository;
import test.task.app.repository.TaskStatusRepository;
import test.task.app.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private CommentaryRepository commentaryRepository;

    @Mapping(target = "assignees", source = "assigneeIds")
    public abstract Task map(TaskCreatedDTO dto);

    @Mapping(target = "status", source = "status.name")
    @Mapping(target = "priority", source = "priority.name")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assigneeIds", source = "assignees")
    @Mapping(target = "commentariesIds", source = "commentaries")
    public abstract TaskDTO map(Task model);

    @Mapping(target = "assignees", source = "assigneeIds")
    public abstract void update(TaskUpdatedDTO dto, @MappingTarget Task model);

    public List<User> toAssignees(List<Long> ids) {
        return new ArrayList<>(userRepository.findAllById(ids));
    }

    public Priority toPriority(String priority) {
        return priorityRepository.findByName(priority)
                .orElseThrow(() -> new ResourceNotFoundException("priority +" + priority + " not found!"));
    }

    public TaskStatus toTaskStatus(String status) {
        return taskStatusRepository.findByName(status)
                .orElseThrow(() -> new ResourceNotFoundException("status +" + status + " not found!"));
    }

    public List<Long> toCommentariesIds(List<Commentary> comments) {
        if (comments == null) {
            return new ArrayList<>();
        } else {
            return comments.stream().map(Commentary::getId).toList();
        }
    }

    public List<Long> toAssigneesIds(List<User> users) {
        if (users == null) {
            return new ArrayList<Long>();
        } else {
            return users.stream().map(User::getId).toList();
        }
    }
}
