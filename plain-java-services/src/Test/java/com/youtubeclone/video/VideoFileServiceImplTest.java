package com.youtubeclone.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Repositories.video.FileRepository;
import com.youtubeclone.Repositories.video.MetadataRepository;
import com.youtubeclone.exceptions.StorageException;
import com.youtubeclone.services.Impl.video.VideoFileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for VideoFileServiceImpl without using Mockito.
 * Using in-memory fake repositories for isolation.
 */
class VideoFileServiceImplTest {

    private VideoFileServiceImpl videoFileService;
    private InMemoryFileRepository fileRepository;
    private InMemoryMetadataRepository metadataRepository;
    private UUID videoId;

    @BeforeEach
    void setUp() {
        fileRepository = new InMemoryFileRepository();
        metadataRepository = new InMemoryMetadataRepository();
        videoFileService = new VideoFileServiceImpl(fileRepository, metadataRepository);

        videoId = UUID.randomUUID();
        fileRepository.save(videoId, "test-data".getBytes());
    }

    @Test
    void uploadVideo_shouldStoreFileData() {
        byte[] fileData = "new-video".getBytes();
        videoFileService.uploadVideo(videoId, fileData);

        assertArrayEquals(fileData, fileRepository.findById(videoId));
    }

    @Test
    void uploadVideo_shouldThrowException_whenFileDataIsNull() {
        assertThrows(StorageException.class,
                () -> videoFileService.uploadVideo(videoId, null));
    }

    @Test
    void uploadVideo_shouldThrowException_whenFileDataIsEmpty() {
        assertThrows(StorageException.class,
                () -> videoFileService.uploadVideo(videoId, new byte[0]));
    }

    @Test
    void downloadVideo_shouldReturnStoredFileData() {
        byte[] result = videoFileService.downloadVideo(videoId);
        assertArrayEquals("test-data".getBytes(), result);
    }

    @Test
    void streamVideo_shouldReturnInputStream() throws Exception {
        InputStream stream = videoFileService.streamVideo(videoId);
        byte[] buffer = stream.readAllBytes();

        assertArrayEquals("test-data".getBytes(), buffer);
    }

    @Test
    void deleteVideoFile_shouldRemoveFile() {
        videoFileService.deleteVideoFile(videoId);
        assertNull(fileRepository.storage.get(videoId));
    }

    // ---------------------
    // Fake In-Memory Repos
    // ---------------------

    static class InMemoryFileRepository extends FileRepository {
        private final Map<UUID, byte[]> storage = new HashMap<>();

        @Override
        public void save(UUID id, byte[] data) {
            storage.put(id, data);
        }

        @Override
        public byte[] findById(UUID id) {
            return storage.get(id);
        }

        @Override
        public void delete(UUID id) {
            storage.remove(id);
        }
    }

    static class InMemoryMetadataRepository extends MetadataRepository {
        private final Map<UUID, Video> metadata = new HashMap<>();
        Video video = new Video();

        @Override
        public Video findById(UUID id) {
            return video;
        }

        public void save(UUID id, Video video) {
            metadata.put(id, video);
        }
    }
}