package com.youtubeclone.videoService;

import com.youtubeclone.videoService.repositories.MetadataRepository;
import com.youtubeclone.videoService.repositories.VideoRepository;
import com.youtubeclone.videoService.services.VideoFileService;
import com.youtubeclone.videoService.services.VideoMetadataService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // activates application-test.properties
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VideoControllerIntegrationTest {
    @Autowired
    private VideoFileService videoFileService;

    @Autowired
    private VideoMetadataService videoMetadataService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    @Test
    void should_create_video(){


    }
}