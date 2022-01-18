package com.accenture.projecttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.projecttask.model.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long>{
    boolean existsByName(String name);
}
