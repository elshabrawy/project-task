package com.accenture.projecttask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.accenture.projecttask.model.User;
import com.accenture.projecttask.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/add")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);

	}

	@GetMapping()
	public List<User> getAllUsers(){
		return userService.findAll();
	}

}
