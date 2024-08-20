package test.task.app.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import test.task.app.dto.priorityDTO.PriorityCreatedDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusCreatedDTO;
import test.task.app.model.Priority;
import test.task.app.model.TaskStatus;
import test.task.app.model.User;
import test.task.app.repository.PriorityRepository;
import test.task.app.repository.TaskStatusRepository;
import test.task.app.repository.UserRepository;
import test.task.app.service.CustomUserDetailsService;
import test.task.app.service.PriorityService;
import test.task.app.service.TaskStatusService;

import java.util.ArrayList;

/**
 * Компонент для инициализации данных приложения.
 * Этот класс реализует интерфейс {@link ApplicationRunner} и используется для
 * создания начальных данных в базе данных при запуске приложения.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final CustomUserDetailsService userService;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusService taskStatusService;
    private final PriorityRepository priorityRepository;
    private final PriorityService priorityService;


    /**
     * Метод, который выполняется при запуске приложения.
     * Этот метод проверяет наличие администратора и создает его, если он отсутствует.
     * Также он инициализирует статусы задач и приоритеты по умолчанию, если они не существуют.
     * @param args аргументы командной строки, переданные приложению
     * @throws Exception если возникает ошибка во время инициализации данных.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            var email = "admin@example.com";
            var userData = new User();
            userData.setEmail(email);
            userData.setPassword("qwerty");
            userService.createUser(userData);
        }

        var defaultStatuses = new ArrayList<TaskStatusCreatedDTO>();
        defaultStatuses.add(new TaskStatusCreatedDTO("Draft"));
        defaultStatuses.add(new TaskStatusCreatedDTO("toReview"));
        defaultStatuses.add(new TaskStatusCreatedDTO("toBeFixed"));
        defaultStatuses.add(new TaskStatusCreatedDTO("toPublish"));
        defaultStatuses.add(new TaskStatusCreatedDTO("Published"));
        var currentStatuses = taskStatusRepository.findAll().stream().map(TaskStatus::getName).toList();
        for (var status : defaultStatuses) {
            if (!currentStatuses.contains(status.getName())) {
                taskStatusService.create(status);
            }
        }

        var defaultPriorities = new ArrayList<PriorityCreatedDTO>();
        defaultPriorities.add(new PriorityCreatedDTO("low"));
        defaultPriorities.add(new PriorityCreatedDTO("high"));
        var currentPriorities = priorityRepository.findAll().stream().map(Priority::getName).toList();
        for (var priority : defaultPriorities) {
            if (!currentPriorities.contains(priority.getName())) {
                priorityService.create(priority);
            }
        }
    }
}
