package com.youtubeclone.dtos.comment;

import java.util.List;

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


    public CommentDTO(String commentId, String videoId,
                      String userId, String parentId,
                      String content, String timestamp,
                      String status, int likes,
                      int dislikes, List<String> editHistory) {
        this.commentId = commentId;
        this.videoId = videoId;
        this.userId = userId;
        this.parentId = parentId;
        this.content = content;
        this.timestamp = timestamp;
        this.status = status;
        this.likes = likes;
        this.dislikes = dislikes;
        this.editHistory = editHistory;
    }

    public CommentDTO(){

    }

    public List<String> getEditHistory() {
        return editHistory;
    }

    public void setEditHistory(List<String> editHistory) {
        this.editHistory = editHistory;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }


    @Override
    public String toString() {
        return "CommentDTO{" +
                "comentId='" + commentId + '\'' +
                "videoId='" + videoId + '\'' +
                "userId='" + userId + '\'' +
                "parentId='" + parentId + '\'' +
                "content='" + content + '\'' +
                "timestamp='" + timestamp + '\'' +
                "status='" + status + '\'' +
                "likes=" + likes +
                "dislikes=" + dislikes +
                "editHistory=" + editHistory +
                "}";
    }

}

