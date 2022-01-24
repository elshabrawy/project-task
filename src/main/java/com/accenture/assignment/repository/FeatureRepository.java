package com.accenture.assignment.repository;


import com.accenture.assignment.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long>{
    Feature findByName(String featureName);
    boolean existsByName(String name);
    void deleteByName(String name);
}
