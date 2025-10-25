package com.youtubeclone.videoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtubeclone.videoService.dtos.CreateVideoRequest;
import com.youtubeclone.videoService.entities.Language;
import com.youtubeclone.videoService.entities.Quality;
import com.youtubeclone.videoService.entities.Visibility;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // activates application-test.properties
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VideoControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private static UUID creatorId;

    @BeforeAll
    static void setup() {
        creatorId = UUID.randomUUID();
    }

    @Test
    @Order(1)
    void testCreateVideo() throws Exception {
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("Integration Test Video");
        request.setDescription("Testing PostgreSQL integration flow");
        request.setVideoUrl("https://cdn.youtubeclone.com/integration.mp4");
        request.setThumbnailUrl("https://cdn.youtubeclone.com/thumb.jpg");
        request.setCreatorId(creatorId);
        request.setLengthSeconds(180);
        request.setSizeMB(72.5);
        request.setCaption(true);
        request.setDownloadable(true);
        request.setLanguage(Language.ENGLISH);
        request.setQuality(Quality.P360);
        request.setVisibility(Visibility.PUBLIC);

        mockMvc.perform(post("/api/v1/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.videoId").isNotEmpty());
    }

    @Test
    @Order(2)
    void testFetchByCreator() throws Exception {
        mockMvc.perform(get("/api/v1/videos/creator/{creatorId}", creatorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].creatorId").value(creatorId.toString()));
    }

    @Test
    @Order(3)
    void testThumbnailUpdate() throws Exception {
        // Get the first video
        String json = mockMvc.perform(get("/api/v1/videos/creator/{creatorId}", creatorId))
                .andReturn().getResponse().getContentAsString();

        String videoId = objectMapper.readTree(json)
                .path("data").get(0).path("videoId").asText();

        mockMvc.perform(patch("/api/v1/videos/{videoId}/thumbnail", videoId)
                        .param("newThumbnail", "https://cdn.youtubeclone.com/new-thumb.jpg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Thumbnail updated successfully"));
    }
}