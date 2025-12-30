package com.pm.common.dto;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ApiErrorResponse(
		@JsonFormat(shape = JsonFormat.Shape.ARRAY)
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldViolation> violations
) {
    public record FieldViolation(String field, String message) {}
}
