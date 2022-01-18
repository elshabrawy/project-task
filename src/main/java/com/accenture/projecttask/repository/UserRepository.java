package com.accenture.projecttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.projecttask.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
}
