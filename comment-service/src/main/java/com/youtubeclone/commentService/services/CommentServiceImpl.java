package com.youtubeclone.commentService.services;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.Models.comment.CommentStatus;
import com.youtubeclone.Repositories.comment.CommentRepositoryImpl;
import com.youtubeclone.defaults.comment.CommentDefaultApplier;
import com.youtubeclone.exceptions.comment.CommentNotFoundException;
import com.youtubeclone.services.Interfaces.comment.CommentService;
import com.youtubeclone.validators.comment.CommentValidator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CommentServiceImpl implements CommentService {
    CommentRepositoryImpl commentRepository;
    CommentValidator commentValidator;
    CommentDefaultApplier commentDefaultApplier;

    // Creating in-memory store for the likes and dislikes
    private final Map<UUID, Set<UUID>> likes = new HashMap<>();
    private final Map<UUID, Set<UUID>> dislikes = new HashMap<>();

    public CommentServiceImpl(CommentRepositoryImpl commentRepository, CommentValidator commentValidator
                                , CommentDefaultApplier commentDefaultApplier) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
        this.commentDefaultApplier  = commentDefaultApplier;
    }

    /** This is nothing but mini-Facade design pattern where 2 different
     *      pipeline is in work 1 or the validation and 1 for the defaultApplier
     *      Once we are done with the validation part then we go ahead and save the
     *      Comment in-memory space storage.
     * @param comment object of Comment class
     */
    @Override
    public void addComment(Comment comment) {
        commentValidator.validate(comment);
        commentDefaultApplier.apply(comment);
        commentRepository.save(comment);
    }

    /** Over here we go ahead and delete the Comment from In-memory
     * storage
     * @param commentId unique id of Comment.
     */
    @Override
    public void deleteComment(UUID commentId) {
        Comment comment = ensureExist(commentId);
        comment.setStatus(CommentStatus.DELETED);
        commentRepository.save(comment);
    }

    /** Retrives the Comment related to the id
     * @param commentId unique id of Comment
     * @return Comment object
     */
    @Override
    public Comment getCommentById(UUID commentId) {
        return ensureExist(commentId);
    }

    /** provides the comment related to the video
     * @param videoId unique id
     * @return list of Comment related to the videoID
     */
    @Override
    public List<Comment> getCommentsByVideo(UUID videoId) {
        return commentRepository.findByVideoID(videoId).stream().
                filter(comment -> comment.getParentId() == null)    // only top-level
                .toList();
    }

    /** provides the comments related to the parentId
     * so basically provides the comments related to the top-level comment
     * @param parentId unique id
     * @return list of Comment
     */
    @Override
    public List<Comment> getReplies(UUID parentId) {
        return commentRepository.findByCommentId(parentId);
    }

    /** Edit the comment and adds the new comment once altered
     * @param commentId unique Id for the comment
     * @param newCommentText String for the new Comment
     * @return Comment object
     */
    @Override
    public Comment editComment(UUID commentId, String newCommentText) {
        Comment comment = commentRepository.findById(commentId);
        List<String> history = comment.getEditHistory();
        if(history == null) {
            history = new ArrayList<>();
        }
        history.add(comment.getContent());
        comment.setEditHistory(history);

        comment.setContent(newCommentText);
        comment.setTimestamp(LocalDateTime.now());

        commentValidator.validate(comment);
        commentRepository.save(comment);

        return comment;
    }

    /** Edit the number of likes to the comment.
     * @param commentId unique id of the Comment
     * @param userId unique id of the User
     */
    @Override
    public void likeComment(UUID commentId, UUID userId) {
        Comment comment = ensureExist(commentId);

        likes.putIfAbsent(commentId, new HashSet<>());

        if(likes.get(commentId).add(userId)) {
            comment.setLikes(comment.getLikes()+1);
        }
        commentRepository.save(comment);
    }

    /** Edit the number of likes to the comment.
     * @param commentId unique id of the Comment
     * @param userId unique id of the User
     */
    @Override
    public void dislikeComment(UUID commentId, UUID userId) {
        Comment comment = ensureExist(commentId);

        dislikes.putIfAbsent(commentId, new HashSet<>());

        if(dislikes.get(commentId).remove(userId)) {
            comment.setDislikes(comment.getDislikes()-1);
        }
        commentRepository.save(comment);
    }

    /**
     * @param commentId unique id for the comment
     * @param reason String for the reason
     */
    @Override
    public void flagComment(UUID commentId, String reason) {
        Comment comment = ensureExist(commentId);
        comment.setStatus(CommentStatus.FLAGGED);

        // Optional: append reason to edit history
        List<String> history = comment.getEditHistory();
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add("FLAGGED: " + reason);
        comment.setEditHistory(history);

        commentRepository.save(comment);
    }

    /** gives the list of comment related to the videoId
     * @param videoId unique id for the Video Object
     * @return List of Comment
     */
    @Override
    public List<Comment> getCommentThread(UUID videoId) {
        List<Comment> allComments = commentRepository.findByVideoID(videoId);

        // build a map for quick lookup
        Map<UUID, List<Comment>> repliesMap = allComments.stream()
                .filter(c -> c.getParentId() != null)
                .collect(Collectors.groupingBy(Comment::getParentId));

        // attach replies recursively
        List<Comment> topLevel = allComments.stream()
                .filter(c -> c.getParentId() == null)
                .collect(Collectors.toList());

        attachReplies(topLevel, repliesMap);
        return topLevel;
    }
    private void attachReplies(List<Comment> parents, Map<UUID, List<Comment>> repliesMap) {
        for (Comment parent : parents) {
            List<Comment> replies = repliesMap.getOrDefault(parent.getCommentId(), new ArrayList<>());
            // recursively attach deeper levels
            attachReplies(replies, repliesMap);
        }
    }

    /**
     * @param videoId
     * @param page
     * @param size
     * @param sortBy
     * @return
     */
    @Override
    public List<Comment> getCommentByVideo(UUID videoId, int page, int size, String sortBy) {
        List<Comment> comments = commentRepository.findByVideoID(videoId);

        // Sorting
        Comparator<Comment> comparator;
        switch (sortBy.toLowerCase()) {
            case "likes":
                comparator = Comparator.comparingInt(Comment::getLikes).reversed();
                break;
            case "dislikes":
                comparator = Comparator.comparingInt(Comment::getDislikes).reversed();
                break;
            case "timestamp":
            default:
                comparator = Comparator.comparing(Comment::getTimestamp).reversed();
        }

        comments = comments.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        // Pagination
        int fromIndex = Math.min(page * size, comments.size());
        int toIndex = Math.min(fromIndex + size, comments.size());

        return comments.subList(fromIndex, toIndex);
    }

    public Comment ensureExist(UUID commentId) {
        Comment comment = commentRepository.findById(commentId);
        if(comment == null) {
            throw new CommentNotFoundException("Comment not found with ID:" + commentId);
        }
        return comment;
    }
}
