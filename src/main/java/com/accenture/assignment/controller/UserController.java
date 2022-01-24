package com.accenture.assignment.controller;

import com.accenture.assignment.dto.UserFeatureDTO;
import com.accenture.assignment.model.Feature;
import com.accenture.assignment.model.Role;
import com.accenture.assignment.model.User;
import com.accenture.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);

    }

    @PostMapping("/addFeature")
    public void addFeature(@RequestBody Feature feature) {
        userService.addFeature(feature);
    }

    @PostMapping("/addRole")
    public void addRole(@RequestBody Role role) {
        userService.addRole(role);
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUsers() {
        return userService.findAllUser();
    }

    @GetMapping("/getAllUserFeature/{userName}")
    public UserFeatureDTO getAllUserFeature(@PathVariable String userName) {
        return userService.findUserFeaturesWithOtherEnabledFeatures(userName);
    }


    @PostMapping("/enableFeatrue/{userName}/{featureName}")
    @Secured("ROLE_ADMIN")
    public void enableFeatureToUser(@PathVariable String userName, @PathVariable String featureName) {
        userService.enableFeatureToUser(userName, featureName);
    }
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/disableFeatrue/{userName}/{featureName}")
    public void disableFeatureToUser(@PathVariable String userName, @PathVariable String featureName) {
        userService.disableFeatureToUser(userName, featureName);
    }
}
