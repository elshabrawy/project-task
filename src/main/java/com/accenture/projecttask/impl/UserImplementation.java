package com.accenture.projecttask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.projecttask.model.User;
import com.accenture.projecttask.repository.UserRepository;
import com.accenture.projecttask.service.UserService;

@Service
public class UserImplementation implements UserService{
	private UserRepository userRepository;

	@Autowired
	public UserImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}


}
