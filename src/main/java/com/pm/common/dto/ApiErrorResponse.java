package com.pm.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    List<FieldViolation> violations) {
  public record FieldViolation(String field, String message) {}
}
