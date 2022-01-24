package com.accenture.assignment.impl;

import com.accenture.assignment.dto.UserFeatureDTO;
import com.accenture.assignment.exception.BadRequestException;
import com.accenture.assignment.model.Feature;
import com.accenture.assignment.model.Role;
import com.accenture.assignment.model.User;
import com.accenture.assignment.repository.FeatureRepository;
import com.accenture.assignment.repository.RoleRepository;
import com.accenture.assignment.repository.UserRepository;
import com.accenture.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserImplementation implements UserService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final FeatureRepository featureRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserFeatureDTO findUserFeaturesWithOtherEnabledFeatures(String userName) {
		log.info("get all User Feature.");
		List<User> userList = userRepository.findAll();

		List<String> userFeatures = userList.stream().filter(e -> e.getName().equals(userName))
				.flatMap(e -> e.getFeatures().stream()).map(e -> e.getName()).distinct().toList();

		List<String> allDistinctFeatures = userList.stream().flatMap(e -> e.getFeatures().stream())
				.map(e -> e.getName()).distinct().toList();

		return UserFeatureDTO.builder().userName(userName).allEnabledFeatures(allDistinctFeatures)
				.allUsersEnabledFeatures(userFeatures).build();
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		log.info("adding User to Database.");
		Boolean existsName = userRepository.existsByName(user.getName());
		if (existsName) {
			throw new BadRequestException("Name " + user.getName() + " taken");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public List<User> findAllUser() {
		log.info("get all Users from Database.");
		return userRepository.findAll();

	}

	@Override
	public void addRole(Role role) {
		log.info("adding Role to Database.");
		Boolean existsName = roleRepository.existsByName(role.getName());
		if (existsName) {
			throw new BadRequestException("Name " + role.getName() + " taken");
		}
		roleRepository.save(role);
	}

	@Override
	public void addFeature(Feature feature) {
		log.info("adding Feature to Database.");
		Boolean existsName = featureRepository.existsByName(feature.getName());
		if (existsName) {
			throw new BadRequestException("Name " + feature.getName() + " taken");
		}
		featureRepository.save(feature);
	}

	@Override
	public void addRoleToUser(String userName, String roleName) {
		log.info("adding Role to User.");
		User user = userRepository.findByName(userName);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public void enableFeatureToUser(String userName, String featureName) {
		log.info("enable Feature to User.");
		User user = userRepository.findByName(userName);
		if (user != null) {
			Feature feature = featureRepository.findByName(featureName);
			if (feature != null) {
				if (!user.getFeatures().contains(feature)) {
					user.getFeatures().add(feature);
				} else {
					log.info("the feature {} already enabled to the user {} found." + featureName);
					throw new BadRequestException("Name " + featureName + " already enabled to the user " + userName);
				}
			} else {
				log.info("the feature {} not found." + featureName);
				throw new BadRequestException("Name " + featureName + " not found");
			}
		} else {
			log.info("the user {} not found." + userName);
			throw new BadRequestException("Name " + userName + " not found");
		}

	}

	@Override
	public void disableFeatureToUser(String userName, String featureName) {
		log.info("disable Feature to User.");
		User user = userRepository.findByName(userName);
		if (user != null) {
			Feature feature = featureRepository.findByName(featureName);
			if (feature != null) {
				if (user.getFeatures().contains(feature)) {
					user.getFeatures().remove(feature);
				} else {
					log.info("the feature {} not enabled to the user {}." + featureName);
					throw new BadRequestException("Name " + featureName + " not enabled to the user " + userName);
				}
			} else {
				log.info("the feature {} not found." + featureName);
				throw new BadRequestException("Name " + featureName + " not found");
			}
		} else {
			log.info("the user {} not found." + userName);
			throw new BadRequestException("Name " + userName + " not found");
		}
	}

}
