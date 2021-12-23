package com.accenture.projecttask.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{userId}/{featureId}")
	public void disableFeature(@PathVariable Long userId, @PathVariable Long featureId) {
		userFeaturesService.disableFeature(userId, featureId);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/{userId}")
	public void enableFeature(@PathVariable Long userId, @RequestBody Feature feature) {
		userFeaturesService.enableFeature(userId, feature);
	}
}
