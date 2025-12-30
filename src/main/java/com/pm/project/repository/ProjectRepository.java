package com.pm.project.repository;

import com.pm.project.entity.Projects;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Projects, Long> {
  List<Projects> findAllByOwnerIdOrderByCreatedAtDesc(Long ownerId);

  Optional<Projects> findByIdAndOwnerId(Long id, Long ownerId);

  boolean existsByIdAndOwnerId(Long id, Long ownerId);
}
