package com.pm.task.service;

import com.pm.common.exception.ResourceNotFoundException;
import com.pm.common.util.SecurityUtil;
import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.task.dto.TaskResponse;
import com.pm.task.entity.Tasks;
import com.pm.task.repository.TaskSearchRepository;
import com.pm.user.entity.Users;
import com.pm.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskSearchServiceimpl implements TaskSearchService {

  private final TaskSearchRepository repo;
  private final UserRepository userRepo;

  public TaskSearchServiceimpl(TaskSearchRepository repo, UserRepository userRepo) {
    this.repo = repo;
    this.userRepo = userRepo;
  }

  @Transactional(readOnly = true)
  public Page<TaskResponse> searchMine(
      String q, TaskStatus status, TaskPriority priority, Pageable pageable) {
    Users user = currentUserOrThrow();

    return repo.searchMine(user.getId(), q, status, priority, pageable).map(this::toResponse);
  }

  private Users currentUserOrThrow() {
    String email = SecurityUtil.currentUserEmail();
    return userRepo
        .findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  private TaskResponse toResponse(Tasks t) {
    return new TaskResponse(
        t.getId(),
        t.getTitle(),
        t.getDescription(),
        t.getStatus(),
        t.getPriority(),
        t.getDueDate(),
        t.getCreatedAt(),
        t.getUpdatedAt());
  }
}
