package test.task.app.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import test.task.app.dto.userDTO.UserCreatedDTO;
import test.task.app.dto.userDTO.UserDTO;
import test.task.app.dto.userDTO.UserUpdatedDTO;
import test.task.app.model.Task;
import test.task.app.model.User;
import test.task.app.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private TaskRepository taskRepository;
    public abstract User map(UserCreatedDTO dto);
    public abstract UserDTO map(User model);
    public abstract void update(UserUpdatedDTO dto, @MappingTarget User model);

    public List<Long> toTaskIds(List<Task> tasks) {
        if (tasks == null) {
            return new ArrayList<Long>();
        } else {
            return tasks.stream().map(Task::getId).toList();
        }
    }

    public List<Task> toTasks(List<Long> tasksIds) {
        if (tasksIds == null) {
            return new ArrayList<Task>();
        } else {
            return tasksIds.stream().map(i -> taskRepository.findById(i).orElseThrow()).toList();
        }
    }

    @BeforeMapping
    public void encryptPassword(UserCreatedDTO dto) {
        var password = dto.getPassword();
        dto.setPassword(encoder.encode(password));
    }

    @BeforeMapping
    public void encryptPassword(UserUpdatedDTO dto, @MappingTarget User model) {
        if (dto.getPassword() != null && dto.getPassword().isPresent()) {
            String password = dto.getPassword().get();
            model.setPassword(encoder.encode(password));
        }
    }

}
