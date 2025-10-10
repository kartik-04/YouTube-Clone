package com.youtube_clone.reaction.entities;

public enum ErrorCode {
    REACTION_NOT_FOUND("REACTION_001", "Reaction not found"),
    INVALID_REACTION_TYPE("REACTION_002", "Invalid reaction type"),
    DATABASE_ERROR("REACTION_003", "Database error"),
    VALIDATION_FAILED("REACTION_004", "Validation failed"),
    INTERNAL_ERROR("REACTION_999", "Unexpected internal error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}