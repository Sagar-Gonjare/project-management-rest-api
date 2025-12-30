package com.pm.task.repository;


import com.pm.task.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Tasks, Long>, JpaSpecificationExecutor<Tasks> {
    Optional<Tasks> findByIdAndProjectId(Long id, Long projectId);
}

