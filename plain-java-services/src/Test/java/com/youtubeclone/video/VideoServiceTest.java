package com.youtubeclone.video;

import com.youtubeclone.Models.video.*;
import com.youtubeclone.defaults.video.VideoDefaultApplier;
import com.youtubeclone.services.Impl.video.VideoMetadataServiceImpl;
import com.youtubeclone.validators.video.VideoValidator;
import com.youtubeclone.Repositories.video.MetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class VideoMetadataServiceImplTest {

    private VideoMetadataServiceImpl service;
    private InMemoryMetadataRepository repository;
    private VideoValidator validator;
    private VideoDefaultApplier defaultApplier;

    // --- Simple in-memory fake repository ---
    static class InMemoryMetadataRepository extends MetadataRepository {
        private final Map<UUID, Video> store = new HashMap<>();

        @Override
        public void save(Video video) {
            store.put(video.getVideoId(), video);
        }

        @Override
        public Video findById(UUID id) {
            return store.get(id);
        }

        @Override
        public void delete(UUID id) {
            store.remove(id);
        }

        @Override
        public List<Video> findByCreator(UUID creatorId) {
            List<Video> result = new ArrayList<>();
            for (Video v : store.values()) {
                if (v.getCreatorId().equals(creatorId)) result.add(v);
            }
            return result;
        }

        @Override
        public Video findByTitle(String title) {
            for (Video v : store.values()) {
                if (v.getTitle().equals(title)) return v;
            }
            return null;
        }
    }

    @BeforeEach
    void setUp() {
        repository = new InMemoryMetadataRepository();
        validator = new VideoValidator();       // can use the real validator
        defaultApplier = new VideoDefaultApplier(); // apply defaults

        service = new VideoMetadataServiceImpl(repository, validator, defaultApplier);
    }

    @Test
    void createVideo_shouldSaveVideo() {
        Video video = new Video();
        VideoMetadata metadata = video.getMetadata();
        metadata.setLengthSeconds(23);
        metadata.setSizeMB(20);
        video.setThumbnailUrl("http://new.thumbnail.jpg");
        video.setVideoId(UUID.randomUUID());
        video.setTitle("My Video");
        video.setCreatorId(UUID.randomUUID());

        Video saved = service.createVideo(video);
        assertNotNull(saved);
        assertEquals("My Video", saved.getTitle());
        assertEquals(video.getVideoId(), saved.getVideoId());

        // Verify stored in repository
        Video fromRepo = repository.findById(video.getVideoId());
        assertNotNull(fromRepo);
        assertEquals(saved.getVideoId(), fromRepo.getVideoId());
    }

    @Test
    void getVideoById_shouldReturnCorrectVideo() {
        Video video = new Video();
        video.setVideoId(UUID.randomUUID());
        video.setTitle("Video 1");
        video.setCreatorId(UUID.randomUUID());
        repository.save(video);

        Video result = service.getVideoById(video.getVideoId());
        assertEquals(video.getTitle(), result.getTitle());
    }

    @Test
    void getVideoByCreator_shouldReturnAllVideosForCreator() {
        UUID creatorId = UUID.randomUUID();

        Video v1 = new Video(); v1.setVideoId(UUID.randomUUID()); v1.setCreatorId(creatorId); v1.setTitle("V1");
        Video v2 = new Video(); v2.setVideoId(UUID.randomUUID()); v2.setCreatorId(creatorId); v2.setTitle("V2");
        Video v3 = new Video(); v3.setVideoId(UUID.randomUUID()); v3.setCreatorId(UUID.randomUUID()); v3.setTitle("V3");

        repository.save(v1); repository.save(v2); repository.save(v3);

        List<Video> creatorVideos = service.getVideoByCreator(creatorId);
        assertEquals(2, creatorVideos.size());
        assertTrue(creatorVideos.stream().anyMatch(v -> v.getTitle().equals("V1")));
        assertTrue(creatorVideos.stream().anyMatch(v -> v.getTitle().equals("V2")));
    }

    @Test
    void deleteVideoMetadata_shouldRemoveVideo() {
        Video video = new Video();
        video.setVideoId(UUID.randomUUID());
        repository.save(video);

        service.deleteVideoMetadata(video.getVideoId());
        assertNull(repository.findById(video.getVideoId()));
    }

    @Test
    void changeThumbnail_shouldUpdateThumbnail() {
        Video video = new Video();
        video.setVideoId(UUID.randomUUID());
        video.setTitle("Test");
        video.setCreatorId(UUID.randomUUID());
        repository.save(video);

        String newThumb = "http://new.thumbnail";
        service.changeThumbnail(video.getVideoId(), newThumb);

        Video updated = repository.findById(video.getVideoId());
        assertEquals(newThumb, updated.getThumbnailUrl());
    }

    @Test
    void getVideoMetadata_shouldReturnMetadata() {
        Video video = new Video();
        video.setVideoId(UUID.randomUUID());
        video.setTitle("Test Video");
        video.setCreatorId(UUID.randomUUID());
        VideoMetadata metadata = new VideoMetadata();
        metadata.setLengthSeconds(100);
        video.setMetadata(metadata);

        repository.save(video);

        VideoMetadata result = service.getVideoMetadata(video.getVideoId());
        assertNotNull(result);
        assertEquals(100, result.getLengthSeconds());
    }

    @Test
    void getVideoByTitle_shouldReturnVideo() {
        Video video = new Video();
        video.setVideoId(UUID.randomUUID());
        video.setTitle("Unique Title");
        video.setCreatorId(UUID.randomUUID());
        repository.save(video);

        Video result = service.getVideoByTitle("Unique Title");
        assertNotNull(result);
        assertEquals(video.getVideoId(), result.getVideoId());
    }

    @Test
    void getVideoByTitle_nullOrBlank_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.getVideoByTitle(null));
        assertThrows(IllegalArgumentException.class, () -> service.getVideoByTitle(" "));
    }
}