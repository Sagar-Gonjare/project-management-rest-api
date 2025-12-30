package com.pm.task.service;

import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.task.dto.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskSearchService {

  Page<TaskResponse> searchMine(
      String q, TaskStatus status, TaskPriority priority, Pageable pageable);
}
