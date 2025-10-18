package com.youtubeclone.videoService.exceptions;

/**
 * Thrown to indicate that a validation rule has failed.
 *
 * <p>Examples:
 * <ul>
 *   <li>Title is missing or empty</li>
 *   <li>Upload date is in the future</li>
 *   <li>Video URL format is invalid</li>
 * </ul>
 */
public class ValidationException extends RuntimeException {

    /**
     * Creates a new ValidationException with the given message.
     *
     * @param message description of the validation failure
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Creates a new ValidationException with the given message and root cause.
     *
     * @param message description of the validation failure
     * @param cause   underlying exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
