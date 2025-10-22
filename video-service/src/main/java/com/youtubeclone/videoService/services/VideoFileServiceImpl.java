package com.youtubeclone.videoService.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

/** We are working on the actual binary data for the video.
 * We are injecting the object of FileRepository to perform the CRUD action on the data
 * Also injecting the metadata object cause there is a requirement of videoId
 */

@Service
@Slf4j
public class VideoFileServiceImpl implements VideoFileService {

    @Value("${video/storage/location}")
    private String storageLocation;

    private Path rootPath;

    @PostConstruct
    public void init(){
        try {
            rootPath = Paths.get(storageLocation).toAbsolutePath().normalize();
            Files.createDirectories(rootPath);
            log.info("✅ Video storage directory initialized at {}", rootPath);
        }catch (IOException e) {
            log.error("❌ Could not initialize video storage folder", e);
            throw new RuntimeException("Failed to create storage directory", e);
        }
    }


    @Override
    public void uploadVideo(UUID videoId, byte[] fileData) {
        try {
            Path filePath = rootPath.resolve(videoId.toString() + ".mp4");
            Files.write(filePath, fileData);
            log.info("🎥 Uploaded video {} ({} bytes)", videoId, fileData.length);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading video: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] downloadVideo(UUID videoId) {
        try {
            Path filePath = rootPath.resolve(videoId.toString() + ".mp4");
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while downloading video: " + e.getMessage(), e);
        }
    }

    @Override
    public InputStream streamVideo(UUID videoId) {
        try {
            Path filePath = rootPath.resolve(videoId.toString() + ".mp4");
            return Files.newInputStream(filePath, StandardOpenOption.READ);
        } catch (IOException e) {
            throw new RuntimeException("Error while streaming video: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteVideoFile(UUID videoId) {
        try {
            Path filePath = rootPath.resolve(videoId.toString() + ".mp4");
            Files.deleteIfExists(filePath);
            log.info("🗑️ Deleted video file: {}", videoId);
        } catch (IOException e) {
            throw new RuntimeException("Error while deleting video file: " + e.getMessage(), e);
        }
    }
}
