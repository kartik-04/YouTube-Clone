package com.youtubeclone.videoService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String status;      // "success" or "error"
    private String message;     // readable message
    private String code;        // internal code like REACTION_001
    private T data;             // actual payload (optional)
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .code("200_OK")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String code) {
        return ApiResponse.<T>builder()
                .status("error")
                .message(message)
                .code(code)
                .timestamp(LocalDateTime.now())
                .build();
    }
}