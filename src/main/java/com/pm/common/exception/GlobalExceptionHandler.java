package com.pm.common.exception;

import com.pm.common.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNotFound(
      ResourceNotFoundException ex, HttpServletRequest request) {

    return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), List.of());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiErrorResponse> handleBadRequest(
      BadRequestException ex, HttpServletRequest request) {

    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI(), List.of());
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiErrorResponse> handleForbidden(
      ForbiddenException ex, HttpServletRequest request) {
    return build(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI(), List.of());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    List<ApiErrorResponse.FieldViolation> violations =
        ex.getBindingResult().getFieldErrors().stream().map(this::toViolation).toList();

    return build(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), violations);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
    ex.printStackTrace();

    return build(
        HttpStatus.INTERNAL_SERVER_ERROR,
        ex.getMessage() != null
            ? ex.getMessage()
            : "Something WEnt wrong", // if message not get then use cusotm exception
        request.getRequestURI(),
        List.of());
  }

  private ResponseEntity<ApiErrorResponse> build(
      HttpStatus status,
      String message,
      String path,
      List<ApiErrorResponse.FieldViolation> violations) {
    ApiErrorResponse body =
        new ApiErrorResponse(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path,
            violations);
    return ResponseEntity.status(status).body(body);
  }

  private ApiErrorResponse.FieldViolation toViolation(FieldError fe) {
    String msg = fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid Value";
    return new ApiErrorResponse.FieldViolation(fe.getField(), msg);
  }
}
