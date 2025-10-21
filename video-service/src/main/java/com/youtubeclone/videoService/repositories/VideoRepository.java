package com.youtubeclone.videoService.repositories;

import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.entities.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
    // ✅ Find videos by creator
    List<Video> findByCreatorId(UUID creatorId);

    // ✅ Find video by title (case-insensitive)
    Optional<Video> findByTitleIgnoreCase(String title);

    // ✅ Optional custom query example (if needed)
    @Query("SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Video> searchByTitleKeyword(String keyword);

    // 🔍 4️⃣ Find by visibility (e.g., PUBLIC / PRIVATE)
    List<Video> findByVisibility(Visibility visibility);

    // 🔍 5️⃣ Find by upload date range
    List<Video> findByUploadDateBetween(LocalDate start, LocalDate end);

    // 🔍 6️⃣ Find by both creator and visibility (useful for dashboard filters)
    List<Video> findByCreatorIdAndVisibility(UUID creatorId, Visibility visibility);

    // 🔍 7️⃣ Search within description (like title keyword search)
    @Query("SELECT v FROM Video v WHERE LOWER(v.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Video> searchByDescriptionKeyword(String keyword);

    // 🔍 8️⃣ Find by external video ID (YouTube-style UUID)
    Optional<Video> findByVideoId(UUID videoId);

    // 🔍 9️⃣ Search both title & description in one query (useful for global search)
    @Query("""
        SELECT v FROM Video v 
        WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) 
           OR LOWER(v.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Video> searchByKeywordInTitleOrDescription(String keyword);
}
