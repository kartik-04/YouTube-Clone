package com.youtubeclone.videoService.repositories;

import com.youtubeclone.videoService.entities.Language;
import com.youtubeclone.videoService.entities.Quality;
import com.youtubeclone.videoService.entities.VideoMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MetadataRepository extends JpaRepository<VideoMetadata, UUID> {

    List<VideoMetadata> findByQuality(Quality quality);
    List<VideoMetadata> findByLanguage(Language language);
    List<VideoMetadata> findByCaptionTrue();
    Optional<VideoMetadata> findByVideo_Id(UUID videoId);
    List<VideoMetadata> findByDownloadableTrue();
    Page<VideoMetadata> findAll(Pageable pageable);// videos that have captions
    // Length filters
    List<VideoMetadata> findByLengthSecondsGreaterThan(int minSeconds);
    List<VideoMetadata> findByLengthSecondsLessThan(int maxSeconds);
    List<VideoMetadata> findByLengthSecondsBetween(int min, int max);
}