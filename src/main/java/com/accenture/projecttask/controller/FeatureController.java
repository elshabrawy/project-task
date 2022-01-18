package com.accenture.projecttask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
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
	@PostMapping("/add")
	public Feature addFeature(@RequestBody Feature feature) {
		return featureService.addFeature(feature);

	}

}
