package test.task.app.utils;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import test.task.app.model.Commentary;
import test.task.app.model.Priority;
import test.task.app.model.Task;
import test.task.app.model.TaskStatus;
import test.task.app.model.User;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;

@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;
    private Model<TaskStatus> statusModel;
    private Model<Task> taskModel;
    private Model<Priority> priorityModel;
    private Model<Commentary> commentaryModel;
    private Faker faker;

    @PostConstruct
    private void init() {
        faker = new Faker();

        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getUpdatedAt))
                .ignore(Select.field(User::getCreatedAt))
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.internet().password())
                .toModel();

        statusModel = Instancio.of(TaskStatus.class)
                .ignore(Select.field(TaskStatus::getId))
                .ignore(Select.field(TaskStatus::getCreatedAt))
                .supply(Select.field(TaskStatus::getName), () -> faker.lorem().characters(3, 200))
                .toModel();

        taskModel = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getStatus))
                .ignore(Select.field(Task::getAssignees))
                .ignore(Select.field(Task::getPriority))
                .ignore(Select.field(Task::getCommentaries))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().characters(3, 20))
                .supply(Select.field(Task::getDescription), () -> faker.lorem().characters(3, 50))
                .toModel();

        priorityModel = Instancio.of(Priority.class)
                .ignore(Select.field(Priority::getId))
                .ignore(Select.field(Priority::getCreatedAt))
                .supply(Select.field(Priority::getName), () -> faker.lorem().characters(3, 200))
                .toModel();

        commentaryModel = Instancio.of(Commentary.class)
                .ignore(Select.field(Commentary::getId))
                .ignore(Select.field(Commentary::getAuthor))
                .ignore(Select.field(Commentary::getCratedAt))
                .supply(Select.field(Commentary::getContent), () -> faker.lorem().characters(3, 100))
                .toModel();
    }
}
