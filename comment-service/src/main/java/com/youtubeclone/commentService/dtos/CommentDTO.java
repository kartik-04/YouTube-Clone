package com.youtubeclone.commentService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String commentId;
    private String videoId;
    private String userId;
    private String parentId;
    private String content;
    private String timestamp;
    private String status;
    private int likes;
    private int dislikes;
    private List<String> editHistory;
}

