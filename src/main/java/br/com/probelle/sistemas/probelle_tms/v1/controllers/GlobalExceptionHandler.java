package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.BusinessRuleException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ConflictException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
      NotFoundException ex, HttpServletRequest request) {
    return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
  }

  @ExceptionHandler({ConflictException.class, BusinessRuleException.class})
  public ResponseEntity<ErrorResponse> handleConflict(
      RuntimeException ex, HttpServletRequest request) {
    return build(HttpStatus.CONFLICT, ex.getMessage(), request, null);
  }

  @ExceptionHandler({ValidationException.class})
  public ResponseEntity<ErrorResponse> handleValidation(
      ValidationException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    Map<String, String> details = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> details.put(error.getField(), error.getDefaultMessage()));
    return build(HttpStatus.BAD_REQUEST, "Validation failed", request, details);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, null);
  }

  private ResponseEntity<ErrorResponse> build(
      HttpStatus status, String message, HttpServletRequest request, Map<String, String> details) {
    ErrorResponse body =
        new ErrorResponse(
            OffsetDateTime.now(),
            status.value(),
            status.name(),
            message,
            request.getRequestURI(),
            details);
    return ResponseEntity.status(status).body(body);
  }

  public record ErrorResponse(
      OffsetDateTime timestamp,
      int status,
      String error,
      String message,
      String path,
      Map<String, String> details) {}
}
