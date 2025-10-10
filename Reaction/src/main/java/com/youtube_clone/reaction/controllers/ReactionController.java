package com.youtube_clone.reaction.controllers;

import com.youtube_clone.reaction.dtos.CreateReactionRequest;
import com.youtube_clone.reaction.dtos.ReactionDTO;
import com.youtube_clone.reaction.entities.Reaction;
import com.youtube_clone.reaction.entities.ReactionType;
import com.youtube_clone.reaction.exceptions.ApiResponse;
import com.youtube_clone.reaction.mappers.ReactionMapper;
import com.youtube_clone.reaction.services.ReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * REST controller exposing endpoints to manage reactions on videos and comments.
 *
 * Endpoints:
 * - POST   /api/v1/reactions/video/{videoId}      -> create or update a user's reaction on a video
 * - DELETE /api/v1/reactions/video/{videoId}      -> remove a user's reaction from a video
 * - GET    /api/v1/reactions/video/{videoId}/count -> aggregated counts of reactions on a video
 * - GET    /api/v1/reactions/user/{userId}/video/{videoId} -> fetch a user's reaction on a video
 * - GET    /api/v1/reactions/video/{videoId}/page  -> paginated reactions for a video
 * - POST   /api/v1/reactions/comment/{commentId}   -> create or update a user's reaction on a comment
 * - DELETE /api/v1/reactions/comemnt/{commentId}   -> remove a user's reaction from a comment
 * - GET    /api/v1/reactions/comment/{commentId}/count -> aggregated counts for a comment
 * - GET    /api/v1/reactions/user/{userId}/comment/{commentId} -> fetch a user's reaction on a comment
 * - GET    /api/v1/reactions/comment/{commentId}/page -> paginated reactions for a comment
 *
 * Notes:
 * - Uses SLF4J for logging.
 * - Wraps responses in {@link ApiResponse}.
 */
@RestController
@RequestMapping("/api/v1/reactions")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;
    private final Logger logger = LoggerFactory.getLogger(ReactionController.class);





    //--------------------------------------------Video - Reaction -------------------------------------------------//






    @PostMapping("/video/{videoId}")
    public ResponseEntity<ApiResponse<ReactionDTO>> reactToVideo(
            @PathVariable UUID videoId,
            @RequestBody @Valid CreateReactionRequest request
            ){
        ReactionType type;
        try{
            type = ReactionType.valueOf(request.getType().toUpperCase());
        }catch(IllegalArgumentException e){
            logger.error("Invalid Reaction type: {}", request.getType());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid reaction type: " , request.getType()));
        }
        Reaction reaction = reactionService.reactToVideo(
                UUID.fromString(request.getUserId()),
                videoId,
                type
        );
        logger.info("User {} reacted to video {} with {}", request.getUserId(), videoId, type);
        return ResponseEntity.ok(ApiResponse.success("Reaction added successfully", ReactionMapper.toDTO(reaction)));
    }

    @DeleteMapping("/video/{videoId}")
    public ResponseEntity<ApiResponse<Void>> removeReactionFromVideo(
            @PathVariable UUID videoId,
            @RequestBody @Valid CreateReactionRequest request
    ){
        reactionService.removeReactionFromVideo(videoId, UUID.fromString(request.getUserId()));
        return ResponseEntity.ok(ApiResponse.success("Reaction removed successfully", null));
    }

    @GetMapping("/video/{videoId}/count")
    public ResponseEntity<ApiResponse<Map<ReactionType, Long>>> reactionCountForVideo(
            @PathVariable UUID videoId
    ){
        Map<ReactionType, Long> reactionCount = reactionService.getReactionCountForVideo(videoId);
        return ResponseEntity.ok(ApiResponse.success("Count of video given successfully", reactionCount));
    }

    @GetMapping("/user/{userId}/video/{videoId}")
    public ResponseEntity<ApiResponse<ReactionDTO>> getUserReactionForVideo(
            @PathVariable UUID userId,
            @PathVariable UUID videoId
    ){
        Reaction reaction = reactionService.getUserReactionForVideo(userId, videoId);
        return ResponseEntity.ok(ApiResponse.success("Reaction found", ReactionMapper.toDTO(reaction)));
    }

    @GetMapping("/video/{videoId}/page")
    public ResponseEntity<ApiResponse<Page<ReactionDTO>>> getReactionForVideo(
            @PathVariable UUID videoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<Reaction> reactionPage = reactionService.getReactionForVideo(videoId, PageRequest.of(page, size));
        Page<ReactionDTO> dtoPage = reactionPage.map(ReactionMapper::toDTO);
        return ResponseEntity.ok(ApiResponse.success("Reactions found", dtoPage));
    }




    // ----------------------------------------------Comment - Reaction ---------------------------------------------//






    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse<ReactionDTO>> reactToComment(
            @PathVariable UUID commentId,
            @RequestBody @Valid CreateReactionRequest request
    ){
        ReactionType type;
        try{
            type = ReactionType.valueOf(request.getType().toUpperCase());
        }catch(IllegalArgumentException e){
            logger.error("Invalid Reaction type: {}", request.getType());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid reaction type: " , request.getType()));
        }
        Reaction reaction = reactionService.reactToComment(
                UUID.fromString(request.getUserId()),
                commentId,
                type
        );
        logger.info("User {} reacted to comment {} with {}", request.getUserId(), commentId, type);
        return ResponseEntity.ok(ApiResponse.success("Reaction added successfully", ReactionMapper.toDTO(reaction)));
    }


    @DeleteMapping("/comemnt/{commentId}")
    public ResponseEntity<ApiResponse<Void>> removeReactionFromComment(
            @PathVariable UUID commentId,
            @RequestBody @Valid CreateReactionRequest request
    ){
        reactionService.removeReactionFromComment(commentId, UUID.fromString(request.getUserId()));
        return ResponseEntity.ok(ApiResponse.success("Reaction removed successfully", null));
    }

    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<ApiResponse<Map<ReactionType, Long>>> reactionCountForComment(
            @PathVariable UUID commentId
    ){
        Map<ReactionType, Long> reactionCount = reactionService.getReactionCountForComment(commentId);
        return ResponseEntity.ok(ApiResponse.success("Count of comment given successfully", reactionCount));
    }

    @GetMapping("/user/{userId}/comment/{commentId}")
    public ResponseEntity<ApiResponse<ReactionDTO>> getUserReactionForComment(
            @PathVariable UUID userId,
            @PathVariable UUID commentId
    ){
        Reaction reaction = reactionService.getUserReactionForComment(userId, commentId);
        return ResponseEntity.ok(ApiResponse.success("Reaction found", ReactionMapper.toDTO(reaction)));
    }

    @GetMapping("/comment/{commentId}/page")
    public ResponseEntity<ApiResponse<Page<ReactionDTO>>> getReactionForComment(
            @PathVariable UUID commentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<Reaction> reactionPage = reactionService.getReactionForComment(commentId, PageRequest.of(page, size));
        Page<ReactionDTO> dtoPage = reactionPage.map(ReactionMapper::toDTO);
        return ResponseEntity.ok(ApiResponse.success("Reactions found", dtoPage));
    }
}
