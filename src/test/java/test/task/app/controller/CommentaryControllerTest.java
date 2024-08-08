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
import test.task.app.dto.commentaryDTO.CommentaryCreatedDTO;
import test.task.app.dto.commentaryDTO.CommentaryUpdatedDTO;
import test.task.app.model.Commentary;
import test.task.app.model.Priority;
import test.task.app.model.Task;
import test.task.app.model.TaskStatus;
import test.task.app.model.User;
import test.task.app.repository.CommentaryRepository;
import test.task.app.repository.PriorityRepository;
import test.task.app.repository.TaskRepository;
import test.task.app.repository.TaskStatusRepository;
import test.task.app.repository.UserRepository;
import test.task.app.utils.ModelGenerator;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentaryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentaryRepository commentaryRepository;

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

    private Commentary testCommentary;

    private User testUser;

    private Task testTask;

    private Priority testPriority;

    private TaskStatus testStatus;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        token = jwt().jwt(builder -> builder.subject("admin@example.com"));
        testCommentary = Instancio.of(modelGenerator.getCommentaryModel()).create();
        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testPriority = Instancio.of(modelGenerator.getPriorityModel()).create();
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        testStatus = Instancio.of(modelGenerator.getStatusModel()).create();

        userRepository.save(testUser);
        priorityRepository.save(testPriority);
        taskStatusRepository.save(testStatus);

        testTask.setPriority(testPriority);
        testTask.setAuthor(testUser);
        testTask.setStatus(testStatus);

        taskRepository.save(testTask);

        testCommentary.setAuthor(testUser);
        testCommentary.setTask(testTask);
        commentaryRepository.save(testCommentary);

    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/api/commentaries").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/api/commentaries/" + testCommentary.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var data = new CommentaryCreatedDTO();
        data.setContent("abracadabre");
        data.setTaskId(testTask.getId());

        var request = post("/api/commentaries")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        var commentaries = commentaryRepository.findAll().stream().map(Commentary::getContent).toList();
        assertThat(commentaries).contains(data.getContent());
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new CommentaryUpdatedDTO();
        updatedData.setContent(JsonNullable.of("Work"));

        var request = put("/api/commentaries/" + testCommentary.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        var commentaries = commentaryRepository.findAll().stream().map(Commentary::getContent).toList();
        assertThat(commentaries).contains(updatedData.getContent().get());
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var fakeCom = new HashMap<String, String>();
        fakeCom.put("content", "");
        fakeCom.put("taskId", "35L");
        var request = post("/api/commentaries")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(fakeCom));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/commentaries/" + testCommentary.getId()).with(token))
                .andExpect(status().isNoContent());
    }

}
