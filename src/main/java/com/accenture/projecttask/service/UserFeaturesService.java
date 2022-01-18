package com.accenture.projecttask.service;

import java.util.List;

import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.UserFeatures;

public interface UserFeaturesService {
	List<Feature> getAllFeaturesByUserId(Long userId);

    List<UserFeatures>findAll();

	void disableFeature(Long userFeautureId);

	UserFeatures enableFeature(UserFeatures userFeauture);


}
