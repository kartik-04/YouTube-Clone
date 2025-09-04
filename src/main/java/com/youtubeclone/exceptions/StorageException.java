package com.youtubeclone.exceptions;

/**
 * Thrown to indicate a problem with file or storage operations.
 *
 * <p>Examples:
 * <ul>
 *   <li>Saving a video file with empty data</li>
 *   <li>Failure to access in-memory or disk storage</li>
 * </ul>
 */
public class StorageException extends RuntimeException {

    /**
     * Creates a new StorageException with the given message.
     *
     * @param message description of the storage error
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Creates a new StorageException with the given message and root cause.
     *
     * @param message description of the storage error
     * @param cause   underlying exception
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}