package com.accenture.projecttask.impl;

import java.util.List;

import com.accenture.projecttask.exception.BadRequestException;
import com.accenture.projecttask.exception.UserFeatureNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.UserFeatures;
import com.accenture.projecttask.repository.UserFeaturesRepository;
import com.accenture.projecttask.repository.UserRepository;
import com.accenture.projecttask.service.UserFeaturesService;

@Service
public class UserFeaturesImplementation implements UserFeaturesService {

	@Autowired
	private UserFeaturesRepository userFeaturesRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	public UserFeaturesImplementation(UserFeaturesRepository userFeaturesRepository) {
		this.userFeaturesRepository = userFeaturesRepository;
	}

	@Override
	public List<Feature> getAllFeaturesByUserId(Long userId) {
		// TODO Auto-generated method stub
		return userFeaturesRepository.getUserFeaturesByUserId(userId);
	}

	@Override
	public List<UserFeatures> findAll() {
		return userFeaturesRepository.findAll();
	}

	@Override
	public UserFeatures enableFeature(UserFeatures userFeauture) {
		// TODO Auto-generated method stub
		Boolean existsId = userFeaturesRepository.existsById(userFeauture.getId());
		if (existsId) {
			throw new BadRequestException(
					"Id " + userFeauture.getId()+ " taken");
		}
//		UserFeatures userFeatures=UserFeatures.builder().user(userRepository.findById(userId).get()).feature(feature).build();
		
		return userFeaturesRepository
				.save(userFeauture);

	}

	@Override
	public void disableFeature(Long userFeautureId) {
		// TODO Auto-generated method stub
		if(!userFeaturesRepository.existsById(userFeautureId)) {
			throw new UserFeatureNotFoundException(
					"User Feature with id " + userFeautureId + " does not exists");
		}
		userFeaturesRepository.deleteById(userFeautureId);
//		userFeaturesRepository.deleteByUserIdAndFeatureId(userid, featureId);
	}

}
