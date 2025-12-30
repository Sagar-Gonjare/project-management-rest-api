package com.pm.task.dto;

import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TaskCreateRequest(
    @NotBlank @Size(max = 160) String title,
    @Size(max = 800) String description,
    @NotNull TaskStatus status,
    @NotNull TaskPriority priority,
    LocalDate dueDate) {}
