package com.youtubeclone.exceptions;

/**
 * Thrown to indicate that a requested resource could not be found.
 *
 * <p>Examples:
 * <ul>
 *   <li>Video with a given ID does not exist</li>
 *   <li>Creator has no uploaded videos</li>
 * </ul>
 */
public class NotFoundException extends RuntimeException {

    /**
     * Creates a new NotFoundException with the given message.
     *
     * @param message description of the missing resource
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new NotFoundException with the given message and root cause.
     *
     * @param message description of the missing resource
     * @param cause   underlying exception
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}