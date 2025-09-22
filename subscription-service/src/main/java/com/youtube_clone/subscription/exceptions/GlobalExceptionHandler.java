package com.youtube_clone.subscription.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

  @ExceptionHandler(SubscriptionNotFoundException.class)
  public ResponseEntity<String> handleNotFound(SubscriptionNotFoundException ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(AlreadySubscribedException.class)
  public ResponseEntity<String> handleAlreadySubscribed(AlreadySubscribedException ex){
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(InvalidSubscriptionException.class)
  public ResponseEntity<String> handleInvalidSubscription(InvalidSubscriptionException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
