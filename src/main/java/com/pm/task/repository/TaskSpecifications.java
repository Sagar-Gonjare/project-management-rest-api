package com.pm.task.repository;

import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.task.entity.Tasks;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public final class TaskSpecifications {

  private TaskSpecifications() {}

  public static Specification<Tasks> forProject(Long projectId) {
    return (root, query, cb) -> cb.equal(root.get("project").get("id"), projectId);
  }

  public static Specification<Tasks> hasStatus(TaskStatus status) {
    return (root, query, cb) -> cb.equal(root.get("status"), status);
  }

  public static Specification<Tasks> hasPriority(TaskPriority priority) {
    return (root, query, cb) -> cb.equal(root.get("priority"), priority);
  }

  public static Specification<Tasks> dueDateFrom(LocalDate from) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dueDate"), from);
  }

  public static Specification<Tasks> dueDateTo(LocalDate to) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), to);
  }
}
