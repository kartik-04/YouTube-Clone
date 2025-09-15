package com.youtubeclone.config.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.defaults.video.VideoDefaultApplier;
import com.youtubeclone.services.Impl.video.VideoMetadataServiceImpl;
import com.youtubeclone.services.Impl.video.VideoFileServiceImpl;
import com.youtubeclone.validators.video.VideoValidator;
import com.youtubeclone.Repositories.video.FileRepository;
import com.youtubeclone.Repositories.video.MetadataRepository;

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
                new VideoFileServiceImpl(fileRepository, metadataRepository);

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