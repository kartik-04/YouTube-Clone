package com.youtubeclone.videoService;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // activates application-test.properties
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VideoControllerIntegrationTest {
    
}