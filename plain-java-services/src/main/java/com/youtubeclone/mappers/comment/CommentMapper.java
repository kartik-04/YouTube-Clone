package com.youtubeclone.mappers.comment;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.Models.comment.CommentStatus;
import com.youtubeclone.dtos.comment.CommentDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentMapper {

    private CommentMapper() {}

    public static CommentDTO toDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDTO dto = new CommentDTO();

        dto.setContent(comment.getContent());
        dto.setCommentId(comment.getCommentId().toString());
        dto.setVideoId(comment.getVideoId().toString());
        dto.setParentId(comment.getParentId() != null ? comment.getParentId().toString() : null);
        dto.setUserId(comment.getUserId().toString());
        dto.setTimestamp(comment.getTimestamp().toString());
        dto.setStatus(comment.getStatus().toString());
        dto.setLikes(comment.getLikes());
        dto.setDislikes(comment.getDislikes());
        dto.setEditHistory(comment.getEditHistory());

        return dto;
    }

    public static Comment toEntity(CommentDTO dto) {
        if (dto == null) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId(UUID.fromString(dto.getCommentId()));
        comment.setVideoId(UUID.fromString(dto.getVideoId()));
        comment.setParentId(dto.getParentId() != null ? UUID.fromString(dto.getParentId()) : null);
        comment.setUserId(UUID.fromString(dto.getUserId()));
        comment.setContent(dto.getContent());
        comment.setLikes(dto.getLikes());
        comment.setDislikes(dto.getDislikes());
        comment.setEditHistory(dto.getEditHistory());
        comment.setTimestamp(LocalDateTime.parse(dto.getTimestamp()));
        comment.setStatus(CommentStatus.valueOf(dto.getStatus()));

        return comment;
    }

}
