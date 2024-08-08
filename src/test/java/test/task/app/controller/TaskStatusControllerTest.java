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
import test.task.app.dto.taskStatusDTO.TaskStatusCreatedDTO;
import test.task.app.dto.taskStatusDTO.TaskStatusUpdatedDTO;
import test.task.app.model.TaskStatus;
import test.task.app.repository.TaskStatusRepository;
import test.task.app.utils.ModelGenerator;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskStatusRepository repository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private TaskStatus testTaskStatus;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testTaskStatus = Instancio.of(modelGenerator.getStatusModel()).create();

        repository.save(testTaskStatus);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/api/task_statuses").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/api/task_statuses/" + testTaskStatus.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var data = new TaskStatusCreatedDTO("testStats");

        var request = post("/api/task_statuses")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        var taskStatus = repository.findByName(data.getName()).get();

        assertNotNull(taskStatus);
        assertThat(taskStatus.getName()).isEqualTo(data.getName());
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new TaskStatusUpdatedDTO();
        updatedData.setName(JsonNullable.of("newses"));

        var request = put("/api/task_statuses/" + testTaskStatus.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedTask = repository.findById(testTaskStatus.getId()).get();

        assertNotNull(updatedTask);
        assertThat(updatedTask.getName()).isEqualTo(updatedData.getName().get());
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var fakeStatus = new HashMap<String, String>();
        fakeStatus.put("name", null);
        var request = post("/api/task_statuses")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(fakeStatus));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/task_statuses/" + testTaskStatus.getId()).with(token))
                .andExpect(status().isNoContent());
    }

}
