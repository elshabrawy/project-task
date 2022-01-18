package com.accenture.projecttask.controller;

import java.util.List;

import com.accenture.projecttask.model.User;
import com.accenture.projecttask.model.UserFeatures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.service.UserFeaturesService;

@RestController
@RequestMapping("/userfeature")
public class UserFeatureController {
	private UserFeaturesService userFeaturesService;

	@Autowired
	public UserFeatureController(UserFeaturesService userFeaturesService) {
		this.userFeaturesService = userFeaturesService;
	}

	@GetMapping("/getFeatures/{userId}")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Feature> getFeaturesByUser(@PathVariable("userId") Long userId) {
		return userFeaturesService.getAllFeaturesByUserId(userId);

	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<UserFeatures> getAllUserFeatures() {
		return userFeaturesService.findAll();

	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{userFeatureId}")
	public void disableFeature(@PathVariable Long userFeatureId) {

		userFeaturesService.disableFeature(userFeatureId);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/enable")
	public void enableFeature(@RequestBody UserFeatures userFeatures) {

		userFeaturesService.enableFeature(userFeatures);
	}
}
