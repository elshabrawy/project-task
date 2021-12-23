package com.accenture.projecttask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.service.FeatureService;

@RestController
@RequestMapping("/feature")

public class FeatureController {
	private FeatureService featureService;

	@Autowired
	public FeatureController(FeatureService featureService) {
		this.featureService = featureService;
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping
	public Feature addFeature(@RequestBody Feature feature) {
		return featureService.addFeature(feature);

	}

}
