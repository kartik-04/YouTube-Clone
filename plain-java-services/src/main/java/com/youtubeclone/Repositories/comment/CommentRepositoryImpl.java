package com.youtubeclone.Repositories.comment;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class CommentRepositoryImpl implements CommentRepository{
    private final Map<UUID, Comment> store = new HashMap<>();

    /** This method make sure comment object is
     * stored in memory store map
     * @param comment object of Comment
     */
    @Override
    public void save(Comment comment) {
        store.put(comment.getCommentId(), comment);
    }

    /** Remove the Comment object from the in-memeory store
     * @param commentId object of Comment
     */
    @Override
    public void delete(UUID commentId) {
        if(store != null) {
            store.remove(commentId);
        }else{
            throw new NotFoundException("Comment not found");
        }
    }

    /** finds the Comment related the unique Id
     *
     * @param commentId ID of unique type
     * @return
     */
    @Override
    public Comment findById(UUID commentId) {
        Comment comment = store.get(commentId);
        if(comment == null) {
            throw new NotFoundException("Comment not found");
        }
        return comment;
    }

    /** uses the stream on the store and then filters
     * out the every videoID until it matches with the
     * given input videoID & if does then it sort the comment object with Timestamp
     * @param videoId unique Id of UUID type
     * @return
     */
    @Override
    public List<Comment> findByVideoID(UUID videoId) {
        return store.values().stream()
                .filter(comment -> comment.getVideoId().equals(videoId) && comment.getParentId() == null)
                .sorted(Comparator.comparing(Comment :: getTimestamp))
                .collect(Collectors.toList());
    }

    /** uses teh strean on the store and then filters every CommentId to check for the
     * Comment by the Parent then sorts it and store it as List.
     * @param commentId unique Id of UUID type
     * @return
     */
    @Override
    public List<Comment> findByCommentId(UUID commentId) {
        return store.values().stream()
                .filter(comment -> comment.getParentId().equals(commentId))
                .sorted(Comparator.comparing(Comment :: getTimestamp))
                .toList();
    }
}
