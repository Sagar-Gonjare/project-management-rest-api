package com.pm.task.controller;

import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.task.dto.TaskResponse;
import com.pm.task.service.TaskSearchServiceimpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskSearchController {

  private final TaskSearchServiceimpl service;

  public TaskSearchController(TaskSearchServiceimpl service) {
    this.service = service;
  }

  @GetMapping("/search")
  public ResponseEntity<Page<TaskResponse>> search(
      @RequestParam(name = "q", required = false) String q,
      @RequestParam(name = "status", required = false) TaskStatus status,
      @RequestParam(name = "priority", required = false) TaskPriority priority,
      Pageable pageable) {
    return ResponseEntity.ok(service.searchMine(q, status, priority, pageable));
  }
}
