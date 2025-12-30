package com.pm.task.repository;

import com.pm.task.entity.Tasks;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepositoryImple
    extends JpaRepository<Tasks, Long>, JpaSpecificationExecutor<Tasks> {
  Optional<Tasks> findByIdAndProjectId(Long id, Long projectId);
}
