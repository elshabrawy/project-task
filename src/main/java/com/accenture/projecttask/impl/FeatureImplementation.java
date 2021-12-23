package com.accenture.projecttask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.repository.FeatureRepository;
import com.accenture.projecttask.service.FeatureService;

@Service
public class FeatureImplementation implements FeatureService {
	private FeatureRepository featureRepository;

	@Autowired
	public FeatureImplementation(FeatureRepository featureRepository) {
		this.featureRepository = featureRepository;
	}

	@Override
	public Feature addFeature(Feature feature) {
		// TODO Auto-generated method stub
		return featureRepository.save(feature);
	}

}
