package com.accenture.projecttask.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.UserFeatures;
import com.accenture.projecttask.repository.UserFeaturesRepository;
import com.accenture.projecttask.repository.UserRepository;
import com.accenture.projecttask.service.UserFeaturesService;

@Service
public class UserFeaturesImplementation implements UserFeaturesService {

	private UserFeaturesRepository userFeaturesRepository;
	private UserRepository userRepository;

	@Autowired
	public UserFeaturesImplementation(UserFeaturesRepository userFeaturesRepository, UserRepository userRepository) {
		this.userFeaturesRepository = userFeaturesRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<Feature> getAllFeaturesByUserId(Long userId) {
		// TODO Auto-generated method stub
		return userFeaturesRepository.getUserFeaturesByUserId(userId);
	}

	@Override
	public void enableFeature(Long userId, Feature feature) {
		// TODO Auto-generated method stub
		UserFeatures userFeatures=UserFeatures.builder().user(userRepository.findById(userId).get()).feature(feature).build();
		
		userFeaturesRepository
				.save(userFeatures);

	}

	@Override
	public void disableFeature(Long userid, Long featureId) {
		// TODO Auto-generated method stub
		userFeaturesRepository.deleteByUserIdAndFeatureId(userid, featureId);
	}

}
