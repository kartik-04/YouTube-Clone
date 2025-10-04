package com.youtube_clone.subscription.exceptions;


import com.youtube_clone.subscription.dtos.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<GlobalResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
    String errorMsg = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> err.getField() + " " + err.getDefaultMessage())
            .findFirst()
            .orElse("Invalid input");
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(GlobalResponse.error(errorMsg));
  }

  @ExceptionHandler(SubscriptionNotFoundException.class)
  public ResponseEntity<GlobalResponse<Void>> handleNotFound(SubscriptionNotFoundException ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GlobalResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(AlreadySubscribedException.class)
  public ResponseEntity<GlobalResponse<Void>> handleAlreadySubscribed(AlreadySubscribedException ex){
    return ResponseEntity.status(HttpStatus.CONFLICT).body(GlobalResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(InvalidSubscriptionException.class)
  public ResponseEntity<GlobalResponse<Void>> handleInvalidSubscription(InvalidSubscriptionException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.error(ex.getMessage()));
  }

  // 🔹 Catch-all fallback (for any unhandled exception)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<GlobalResponse<Void>> handleGenericException(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(GlobalResponse.error("Unexpected error: " + ex.getMessage()));
  }
}
