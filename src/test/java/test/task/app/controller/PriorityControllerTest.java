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
import test.task.app.dto.priorityDTO.PriorityCreatedDTO;
import test.task.app.dto.priorityDTO.PriorityUpdatedDTO;
import test.task.app.model.Priority;
import test.task.app.repository.PriorityRepository;
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
class PriorityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private Priority testPriority;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        token = jwt().jwt(builder -> builder.subject("admin@example.com"));
        testPriority = Instancio.of(modelGenerator.getPriorityModel()).create();
        priorityRepository.save(testPriority);

    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/api/priorities").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/api/priorities/" + testPriority.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var data = new PriorityCreatedDTO("newName");

        var request = post("/api/priorities")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        var priority = priorityRepository.findByName(data.getName()).get();
        assertThat(priority.getName()).isEqualTo(data.getName());
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new PriorityUpdatedDTO();
        updatedData.setName(JsonNullable.of("Work"));

        var request = put("/api/priorities/" + testPriority.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedPriority = priorityRepository.findByName(updatedData.getName().get()).get();
        assertNotNull(updatedPriority);
        assertThat(updatedPriority.getName()).isEqualTo(updatedData.getName().get());
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var fakePr = new HashMap<String, String>();
        var request = post("/api/priorities")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(fakePr));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/priorities/" + testPriority.getId()).with(token))
                .andExpect(status().isNoContent());
    }

}
