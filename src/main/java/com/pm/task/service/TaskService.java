package com.pm.task.service;

import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.task.dto.TaskCreateRequest;
import com.pm.task.dto.TaskResponse;
import com.pm.task.dto.TaskUpdateRequest;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

  TaskResponse create(Long projectId, TaskCreateRequest req);

  Page<TaskResponse> list(
      Long projectId,
      TaskStatus status,
      TaskPriority priority,
      LocalDate dueDateFrom,
      LocalDate dueDateTo,
      Pageable pageable);

  TaskResponse update(Long projectId, Long taskId, TaskUpdateRequest req);

  void delete(Long projectId, Long taskId);
}
