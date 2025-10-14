package com.youtube_clone.watchHistory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidWatchHistoryRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidWatchHistoryRequestException(InvalidWatchHistoryRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Invalid watch history request", ex.getMessage()));
    }

    @ExceptionHandler(ShortWatchDurationException.class)
    public ResponseEntity<ApiResponse<Void>> handleShortWatchDurationException(ShortWatchDurationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Short watch duration", ex.getMessage()));
    }

    @ExceptionHandler(WatchHistoryPersistenceException.class)
    public ResponseEntity<ApiResponse<Void>> handleWatchHistoryPersistenceException(WatchHistoryPersistenceException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Watch history persistence error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal server error", ex.getMessage()));
    }
}
