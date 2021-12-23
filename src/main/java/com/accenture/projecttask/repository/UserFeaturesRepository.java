package com.accenture.projecttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.UserFeatures;

@Repository

public interface UserFeaturesRepository extends JpaRepository<UserFeatures, Long> {
	List<Feature> findByUser(String userName);

	@Query(value = "select uf.feature from UserFeatures uf where uf.user.id=:userId")
	List<Feature> getUserFeaturesByUserId(@Param(value = "userId") Long userId);

	@Transactional
	@Modifying
	void deleteByUserIdAndFeatureId(Long userId, Long featureId);
}
