package com.youtubeclone.Repositories;

import com.youtubeclone.Models.video.Video;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VideoRepository {
    Map<UUID, Video> videos = new HashMap<>();
}
