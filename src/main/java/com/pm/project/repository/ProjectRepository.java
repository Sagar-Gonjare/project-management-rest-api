package com.pm.project.repository;

import com.pm.project.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProjectRepository  extends JpaRepository<Projects, Long>{
	List<Projects> findAllByOwnerIdOrderByCreatedAtDesc(Long ownerId);

    Optional<Projects> findByIdAndOwnerId(Long id, Long ownerId);

    boolean existsByIdAndOwnerId(Long id, Long ownerId);

}
