package com.youtubeclone.video;

import com.youtubeclone.Models.video.*;
import com.youtubeclone.dtos.video.VideoDTO;
import com.youtubeclone.dtos.video.VideoMetadataDTO;
import com.youtubeclone.mappers.video.VideoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VideoMapperTest {

    private Video video;
    private VideoDTO videoDTO;

    @BeforeEach
    void setUp() {
        // Setup Video with metadata
        video = new Video();
        video.setVideoId(UUID.randomUUID());
        video.setTitle("Test Video");
        video.setDescription("This is a test video");
        video.setVideoUrl("http://video.url");
        video.setThumbnailUrl("http://thumbnail.url");
        video.setCreatorId(UUID.randomUUID());
        video.setUploadDate(LocalDate.of(2025, 9, 15));
        video.setVisibility(Visibility.PUBLIC);

        VideoMetadata metadata = new VideoMetadata();
        metadata.setLengthSeconds(120);
        metadata.setSizeMB(50.5);
        metadata.setCaption(true);
        metadata.setDownloadable(true);
        metadata.setQuality(Quality.P720);
        metadata.setLanguage(Language.ENGLISH);

        video.setMetadata(metadata);

        // Setup VideoDTO (mirroring video)
        videoDTO = new VideoDTO();
        videoDTO.setVideoId(video.getVideoId().toString());
        videoDTO.setTitle(video.getTitle());
        videoDTO.setDescription(video.getDescription());
        videoDTO.setVideoUrl(video.getVideoUrl());
        videoDTO.setThumbnailUrl(video.getThumbnailUrl());
        videoDTO.setCreatorId(video.getCreatorId().toString());
        videoDTO.setUploadDate(video.getUploadDate().toString());
        videoDTO.setVisibility(video.getVisibility().name());

        VideoMetadataDTO metadataDTO = new VideoMetadataDTO();
        metadataDTO.setLengthSeconds(metadata.getLengthSeconds());
        metadataDTO.setSizeMB(metadata.getSizeMB());
        metadataDTO.setCaption(metadata.isCaption());
        metadataDTO.setDownloadable(metadata.isDownloadable());
        metadataDTO.setQuality(metadata.getQuality().name());
        metadataDTO.setLanguage(metadata.getLanguage().name());

        videoDTO.setMetadata(metadataDTO);
    }

    @Test
    void toDTO_shouldMapAllFieldsCorrectly() {
        VideoDTO dto = VideoMapper.toDTO(video);

        assertNotNull(dto);
        assertEquals(video.getVideoId().toString(), dto.getVideoId());
        assertEquals(video.getTitle(), dto.getTitle());
        assertEquals(video.getDescription(), dto.getDescription());
        assertEquals(video.getVideoUrl(), dto.getVideoUrl());
        assertEquals(video.getThumbnailUrl(), dto.getThumbnailUrl());
        assertEquals(video.getCreatorId().toString(), dto.getCreatorId());
        assertEquals(video.getUploadDate().toString(), dto.getUploadDate());
        assertEquals(video.getVisibility().name(), dto.getVisibility());

        assertNotNull(dto.getMetadata());
        assertEquals(video.getMetadata().getLengthSeconds(), dto.getMetadata().getLengthSeconds());
        assertEquals(video.getMetadata().getSizeMB(), dto.getMetadata().getSizeMB());
        assertEquals(video.getMetadata().isCaption(), dto.getMetadata().isCaption());
        assertEquals(video.getMetadata().isDownloadable(), dto.getMetadata().isDownloadable());
        assertEquals(video.getMetadata().getQuality().name(), dto.getMetadata().getQuality());
        assertEquals(video.getMetadata().getLanguage().name(), dto.getMetadata().getLanguage());
    }

    @Test
    void toDTO_nullVideo_shouldReturnNull() {
        assertNull(VideoMapper.toDTO(null));
    }

    @Test
    void toEntity_shouldMapAllFieldsCorrectly() {
        Video entity = VideoMapper.toEntity(videoDTO);

        assertNotNull(entity);
        assertEquals(UUID.fromString(videoDTO.getVideoId()), entity.getVideoId());
        assertEquals(videoDTO.getTitle(), entity.getTitle());
        assertEquals(videoDTO.getDescription(), entity.getDescription());
        assertEquals(videoDTO.getVideoUrl(), entity.getVideoUrl());
        assertEquals(videoDTO.getThumbnailUrl(), entity.getThumbnailUrl());
        assertEquals(UUID.fromString(videoDTO.getCreatorId()), entity.getCreatorId());
        assertEquals(LocalDate.parse(videoDTO.getUploadDate()), entity.getUploadDate());
        assertEquals(Visibility.valueOf(videoDTO.getVisibility()), entity.getVisibility());

        assertNotNull(entity.getMetadata());
        assertEquals(videoDTO.getMetadata().getLengthSeconds(), entity.getMetadata().getLengthSeconds());
        assertEquals(videoDTO.getMetadata().getSizeMB(), entity.getMetadata().getSizeMB());
        assertEquals(videoDTO.getMetadata().isCaption(), entity.getMetadata().isCaption());
        assertEquals(videoDTO.getMetadata().isDownloadable(), entity.getMetadata().isDownloadable());
        assertEquals(Quality.valueOf(videoDTO.getMetadata().getQuality()), entity.getMetadata().getQuality());
        assertEquals(Language.valueOf(videoDTO.getMetadata().getLanguage()), entity.getMetadata().getLanguage());
    }

    @Test
    void toEntity_nullDTO_shouldReturnNull() {
        assertNull(VideoMapper.toEntity(null));
    }
}