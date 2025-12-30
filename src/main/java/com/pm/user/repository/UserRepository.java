package com.pm.user.repository;

import com.pm.user.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
  boolean existsByEmail(String email);

  Optional<Users> findByEmail(String email);
}
