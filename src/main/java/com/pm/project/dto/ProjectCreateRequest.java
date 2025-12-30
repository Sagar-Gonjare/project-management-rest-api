package com.pm.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectCreateREquest(
    @NotBlank @Size(max = 140) String name, @Size(max = 500) String description) {}
