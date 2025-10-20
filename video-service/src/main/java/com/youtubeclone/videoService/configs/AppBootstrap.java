package com.youtubeclone.videoService.configs;



import com.youtubeclone.videoService.defaults.VideoDefaultApplier;
import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.entities.VideoMetadata;
import com.youtubeclone.videoService.repositories.FileRepository;
import com.youtubeclone.videoService.repositories.MetadataRepository;
import com.youtubeclone.videoService.services.VideoFileServiceImpl;
import com.youtubeclone.videoService.services.VideoMetadataServiceImpl;
import com.youtubeclone.videoService.validators.VideoValidator;

import java.util.UUID;

public class AppBootstrap {
    public static void main(String[] args) {
        // At this stage, you don’t have real repositories yet.
        // So let’s keep them as `null` or placeholders.
        MetadataRepository metadataRepository  = new MetadataRepository();
        FileRepository fileRepository =  new FileRepository();

        // Set up helpers
        VideoValidator validator = new VideoValidator();
        VideoDefaultApplier defaultApplier = new VideoDefaultApplier();

        // Wire services (repositories are null for now)
        VideoMetadataServiceImpl metadataService =
                new VideoMetadataServiceImpl(metadataRepository, validator, defaultApplier);
        VideoFileServiceImpl fileService =
                new VideoFileServiceImpl(fileRepository);

        // Create a demo video
        UUID videoId = UUID.randomUUID();
        Video video = new Video();
        video.setVideoId(videoId);
        video.setTitle("Demo Video");
        video.setDescription("This is my first video");
        video.setVideoUrl("https://www.youtube.com/embed/" + videoId);
        video.setThumbnailUrl("https://www.youtube.com/embed/" + videoId + ".jpg");

        VideoMetadata metadata = new VideoMetadata();
        metadata.setLengthSeconds(300);
        metadata.setSizeMB(100);
        video.setMetadata(metadata);
        // Just show that your pipeline works (can’t really persist yet)
        metadataService.createVideo(video);

        System.out.println("Video created with ID: " + videoId);
        System.out.println("Video title after defaults: " + video.getTitle());
        byte[] fakeFileData = new byte[5 * 1024 * 1024]; // 5 MB
        new java.util.Random().nextBytes(fakeFileData); // Fill with random data
        fileService.uploadVideo(videoId, fakeFileData);
        System.out.println("Video Uploaded with ID: " + videoId);
    }
}