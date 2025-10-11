package com.youtube_clone.reaction.repositories;

import com.youtube_clone.reaction.entities.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, UUID> {
    // 🧠 1️⃣ Find user’s reaction to a specific video
    Optional<Reaction> findByUserIdAndVideoId(UUID userId, UUID videoId);

    // 🧠 2️⃣ Find user’s reaction to a specific comment
    Optional<Reaction> findByUserIdAndCommentId(UUID userId, UUID commentId);

    // 🧠 3️⃣ Get all active reactions for a video (paged)
    Page<Reaction> findByVideoIdAndActiveTrue(UUID videoId, Pageable pageable);

    // 🧠 4️⃣ Get all active reactions for a comment (paged)
    Page<Reaction> findByCommentIdAndActiveTrue(UUID commentId, Pageable pageable);

    @Query("""
           SELECT r.type, COUNT(r)
           FROM Reaction r
           WHERE r.videoId = :videoId AND r.active = true
           GROUP BY r.type
           """)
    List<Object[]> countReactionsByVideoId(@Param("videoId") UUID videoId);

    @Query("""
           SELECT r.type, COUNT(r)
           FROM Reaction r
           WHERE r.commentId = :commentId AND r.active = true
           GROUP BY r.type
           """)
    List<Object[]> countReactionByCommentId(@Param("commentId") UUID commentID);
}
