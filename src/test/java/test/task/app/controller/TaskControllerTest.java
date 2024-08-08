package test.task.app.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import test.task.app.dto.taskDTO.TaskCreatedDTO;
import test.task.app.dto.taskDTO.TaskUpdatedDTO;
import test.task.app.model.Priority;
import test.task.app.model.Task;
import test.task.app.model.TaskStatus;
import test.task.app.model.User;
import test.task.app.repository.TaskRepository;
import test.task.app.repository.TaskStatusRepository;
import test.task.app.repository.UserRepository;
import test.task.app.repository.PriorityRepository;
import test.task.app.service.TaskService;
import test.task.app.utils.ModelGenerator;

import java.util.List;
import java.util.HashMap;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskService taskService;

    private User testUser;

    private TaskStatus testTaskStatus;

    private Task testTask;

    private Priority testPriority;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        token = jwt().jwt(builder -> builder.subject("admin@example.com"));

        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        testTaskStatus = Instancio.of(modelGenerator.getStatusModel()).create();
        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testPriority = Instancio.of(modelGenerator.getPriorityModel()).create();


        userRepository.save(testUser);
        taskStatusRepository.save(testTaskStatus);
        priorityRepository.save(testPriority);

        testTask.setAuthor(testUser);
        testTask.setStatus(testTaskStatus);
        testTask.setPriority(testPriority);

        taskRepository.save(testTask);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/api/tasks").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/api/tasks/" + testTask.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var taskStatus = taskStatusRepository.findByName("toReview").get();
        var data = new TaskCreatedDTO();
        String name = "New Task Name";
        data.setTitle(name);
        data.setStatus(taskStatus.getName());

        var request = post("/api/tasks")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());
        var task = taskRepository.findByTitle(data.getTitle()).get();

        assertNotNull(task);
        assertThat(task.getTitle()).isEqualTo(data.getTitle());
        assertThat(task.getStatus().getName()).isEqualTo(data.getStatus());
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new TaskUpdatedDTO();
        updatedData.setTitle(JsonNullable.of("news"));
        updatedData.setDescription(JsonNullable.of("contents"));
        updatedData.setAssigneeIds(JsonNullable.of(List.of(1L)));

        var currentToken = jwt().jwt(builder -> builder.subject(testUser.getEmail()));

        var request = put("/api/tasks/" + testTask.getId())
                .with(currentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedTask = taskRepository.findById(testTask.getId()).get();

        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getTitle()).isEqualTo(updatedData.getTitle().get());
        assertThat(updatedTask.getAssignees().get(0).getId()).isEqualTo(updatedData.getAssigneeIds().get().get(0));
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var fakeTask = new HashMap<String, String>();
        fakeTask.put("title", "");
        fakeTask.put("status", "");
        var request = post("/api/tasks")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(fakeTask));

        mockMvc.perform(request).andExpect(status().isNotFound());
    }



    @Test
    public void testDelete() throws Exception {
        var currentToken = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
        mockMvc.perform(delete("/api/tasks/" + testTask.getId()).with(currentToken))
                .andExpect(status().isNoContent());
    }
}
