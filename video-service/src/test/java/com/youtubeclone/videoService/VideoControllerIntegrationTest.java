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


}