package com.youtubeclone.commentService.defaults;

import com.youtubeclone.Models.comment.Comment;

public interface CommentDefaultRule {
    void apply(Comment comment);
}
