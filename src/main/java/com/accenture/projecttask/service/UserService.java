package com.accenture.projecttask.service;

import com.accenture.projecttask.model.User;

import java.util.List;

public interface UserService {
	User addUser(User user);
	List<User> findAll();

}
