package com.youtubeclone.videoService.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileRepository {
    Map<UUID, byte[]> fileRepository = new HashMap<>();

    public void save(UUID id, byte[] file) {
        fileRepository.put(id, file);
    }

    public byte[] findById(UUID id) {
        if(!fileRepository.containsKey(id)) {
            return null;
        }
        return fileRepository.get(id);
    }

    public void delete(UUID id) {
        fileRepository.remove(id);
    }
}
