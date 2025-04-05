package org.c4c.mysql.repository;

import org.c4c.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndTenantId(String username, String tenantId);
    List<User> findAllByTenantId(String tenantId);
}