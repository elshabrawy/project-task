package com.accenture.projecttask.service;

import java.util.List;

import com.accenture.projecttask.model.Feature;

public interface UserFeaturesService {
	List<Feature> getAllFeaturesByUserId(Long userId);

	void disableFeature(Long userid, Long featureId);

	void enableFeature(Long userId, Feature feature);


}
