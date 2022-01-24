package com.accenture.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.accenture.assignment.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String roleName);
    boolean existsByName(String roleName);
    void deleteByName(String name);
}
