package com.youtubeclone.validators.comment.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.comment.CommentValidationRule;

import java.util.Arrays;
import java.util.List;

public class ProfanityRule implements CommentValidationRule {
    private static final List<String> BANNED_WORDS = Arrays.asList("fuck, shit, MF, BC");
    /** Just like the real youtube platform this method helps
     * in making sure that we can set the profanity check and
     * validation for specific words in our comment
     *
     * Latter we will try to implement the feature where creator will get
     * option to choose it's own profanity check for the comment which
     * he won't welcome
     *
     * @param comment object of Comment
     */
    @Override
    public void validate(Comment comment) {
        if(comment.getContent() != null){
            for(String word : BANNED_WORDS){
               if(comment.getContent().toLowerCase().contains(word.toLowerCase())){
                   throw new ValidationException("Comment contains inappropriate words");
               }
            }
        }
    }
}
