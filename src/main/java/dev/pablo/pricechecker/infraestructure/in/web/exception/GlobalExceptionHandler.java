package dev.pablo.pricechecker.infraestructure.in.web.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import dev.pablo.pricechecker.infraestructure.in.web.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleInternalServerExceptions(Exception exception,
      HttpServletRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage())
        .path(request.getRequestURI()).build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class,
      HttpMessageNotReadableException.class, IllegalArgumentException.class})
  public ResponseEntity<ErrorResponse> handleBadRequestExceptions(
      Exception exception, HttpServletRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage())
        .path(request.getRequestURI()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler({NoResourceFoundException.class, EntityNotFoundException.class,})
  public ResponseEntity<ErrorResponse> handleNotFoundExceptions(Exception exception,
      HttpServletRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage())
        .path(request.getRequestURI()).build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Void> handleNoSuchElementException(NoSuchElementException exception) {
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Void> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException exception) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
  }

}
