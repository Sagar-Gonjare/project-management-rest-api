package com.pm.task.dto;

import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
    Long id,
    String title,
    String description,
    TaskStatus status,
    TaskPriority priority,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
