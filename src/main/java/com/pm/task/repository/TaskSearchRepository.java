package com.pm.task.repository;


import com.pm.enums.TaskPriority;
import com.pm.enums.TaskStatus;
import com.pm.task.entity.Tasks;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface TaskSearchRepository extends JpaRepository<Tasks, Long> {

    @Query("""
        SELECT t
        FROM Tasks t
        JOIN t.project p
        JOIN p.owner u
        WHERE u.id = :ownerId
          AND (
              :q IS NULL OR :q = '' OR
              LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%')) OR
              LOWER(COALESCE(t.description, '')) LIKE LOWER(CONCAT('%', :q, '%'))
          )
          AND (:status IS NULL OR t.status = :status)
          AND (:priority IS NULL OR t.priority = :priority)
        """)
    Page<Tasks> searchMine(
            @Param("ownerId") Long ownerId,
            @Param("q") String q,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            Pageable pageable
    );
}
