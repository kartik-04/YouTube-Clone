package com.youtubeclone.services.Interfaces.video;

import java.io.InputStream;
import java.util.UUID;

public interface VideoFileService {

    void uploadVideo(UUID videoId,  byte[] fileData);

    byte[] downloadVideo(UUID videoId);

    InputStream streamVideo(UUID videoId);

    void deleteVideoFile(UUID videoId);
}
