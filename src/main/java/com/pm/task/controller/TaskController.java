package com.pm.task.controller;

import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.task.dto.TaskCreateRequest;
import com.pm.task.dto.TaskResponse;
import com.pm.task.dto.TaskUpdateRequest;
import com.pm.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody TaskCreateRequest req
    ) {
        TaskResponse created = service.create(projectId, req);
        URI location = URI.create("/api/projects/" + projectId + "/tasks/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> list(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "status", required = false) TaskStatus status,
            @RequestParam(name = "priority" ,required = false) TaskPriority priority,
            @RequestParam(name = "dueDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDateFrom,
            @RequestParam(name = "dueDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDateTo,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.list(projectId, status, priority, dueDateFrom, dueDateTo, pageable));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> update(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            @Valid @RequestBody TaskUpdateRequest req
    ) {
        return ResponseEntity.ok(service.update(projectId, taskId, req));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId
    ) {
        service.delete(projectId, taskId);
        return ResponseEntity.noContent().build();
    }
}

