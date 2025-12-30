package com.pm.task.service;

import static org.springframework.data.jpa.domain.Specification.where;

import com.pm.common.exception.BadRequestException;
import com.pm.common.exception.ResourceNotFoundException;
import com.pm.common.util.SecurityUtil;
import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.project.entity.Projects;
import com.pm.project.repository.ProjectRepository;
import com.pm.task.dto.TaskCreateRequest;
import com.pm.task.dto.TaskResponse;
import com.pm.task.dto.TaskUpdateRequest;
import com.pm.task.entity.Tasks;
import com.pm.task.repository.TaskRepositoryImple;
import com.pm.task.repository.TaskSpecifications;
import com.pm.user.entity.Users;
import com.pm.user.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepositoryImple taskRepo;
  private final ProjectRepository projectRepo;
  private final UserRepository userRepo;

  public TaskServiceImpl(
      TaskRepositoryImple taskRepo, ProjectRepository projectRepo, UserRepository userRepo) {
    this.taskRepo = taskRepo;
    this.projectRepo = projectRepo;
    this.userRepo = userRepo;
  }

  @Transactional
  public TaskResponse create(Long projectId, TaskCreateRequest req) {
    Projects project = ownedProjectOrThrow(projectId);

    Tasks task =
        Tasks.builder()
            .title(req.title().trim())
            .description(req.description())
            .status(req.status())
            .priority(req.priority())
            .dueDate(req.dueDate())
            .project(project)
            .build();

    Tasks saved = taskRepo.save(task);
    return toResponse(saved);
  }

  @Transactional(readOnly = true)
  public Page<TaskResponse> list(
      Long projectId,
      TaskStatus status,
      TaskPriority priority,
      LocalDate dueDateFrom,
      LocalDate dueDateTo,
      Pageable pageable) {
    ownedProjectOrThrow(projectId); // ownership gate (404 if not mine)

    if (dueDateFrom != null && dueDateTo != null && dueDateFrom.isAfter(dueDateTo)) {
      throw new BadRequestException("dueDateFrom must be <= dueDateTo");
    }

    Specification<Tasks> spec = where(TaskSpecifications.forProject(projectId));

    if (status != null) {
      spec = spec.and(TaskSpecifications.hasStatus(status));
    }
    if (priority != null) {
      spec = spec.and(TaskSpecifications.hasPriority(priority));
    }
    if (dueDateFrom != null) {
      spec = spec.and(TaskSpecifications.dueDateFrom(dueDateFrom));
    }
    if (dueDateTo != null) {
      spec = spec.and(TaskSpecifications.dueDateTo(dueDateTo));
    }

    return taskRepo.findAll(spec, pageable).map(this::toResponse);
  }

  @Transactional
  public TaskResponse update(Long projectId, Long taskId, TaskUpdateRequest req) {
    ownedProjectOrThrow(projectId);

    Tasks task =
        taskRepo
            .findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

    task.setTitle(req.title().trim());
    task.setDescription(req.description());
    task.setStatus(req.status());
    task.setPriority(req.priority());
    task.setDueDate(req.dueDate());

    return toResponse(task); // dirty checking persists
  }

  @Transactional
  public void delete(Long projectId, Long taskId) {
    ownedProjectOrThrow(projectId);

    Tasks task =
        taskRepo
            .findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

    taskRepo.delete(task);
  }

  private Projects ownedProjectOrThrow(Long projectId) {
    Users user = currentUserOrThrow();
    return projectRepo
        .findByIdAndOwnerId(projectId, user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
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
