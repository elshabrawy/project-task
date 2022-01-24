package com.accenture.assignment.service;


import com.accenture.assignment.dto.UserFeatureDTO;
import com.accenture.assignment.model.Feature;
import com.accenture.assignment.model.Role;
import com.accenture.assignment.model.User;

import java.util.List;

public interface UserService {
	void addUser(User user);
	List<User> findAllUser();
	void addRole(Role role);

	/**
	 * @param feature
	 * adding the new feature, it will be Disabled by default
	 * the user that will add the feature must have an admin role
	 * simply the feature have id and name only
	 */
	void addFeature(Feature feature);
	void addRoleToUser(String userName,String roleName);

	/**
	 *
	 * @param userName
	 * @param featureName
	 * adding the input feature to the list of enabled features for that user
	 * this action done by the admin user only
	 * we first do below checks:
	     1- check if userName is found or throw exception
	     2- check if featureName is found or throw exception
	     3- check if the feature already enabled to the user or throw exception
	     4- user may have a list of enabled feature
	 */
	void enableFeatureToUser(String userName,String featureName);

	/**
	 *
	 * @param userName
	 * @param featureName
	 * remove the input feature from the list of enabled features for that user
	 * this action done by the admin user only
	 * we first do below checks:
	     1- check if userName is found or throw exception
	     2- check if featureName is found or throw exception
	     3- check if the feature already enabled to the user or throw exception
	 */
	void disableFeatureToUser(String userName,String featureName);


	/**
	 *
	 * @param userName
	 * @return
	 * the function is to all the enabled features in general and list all the that user enabled features
	 * this action done by the admin user only
	 * we first do below checks:
	     1- check if userName is found or throw exception
	 *
	 */
	UserFeatureDTO findUserFeaturesWithOtherEnabledFeatures(String userName);


}
