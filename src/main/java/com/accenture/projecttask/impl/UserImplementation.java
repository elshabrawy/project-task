package com.accenture.projecttask.impl;

import com.accenture.projecttask.exception.BadRequestException;
import com.accenture.projecttask.model.User;
import com.accenture.projecttask.repository.UserRepository;
import com.accenture.projecttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImplementation implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
//        userMapper=new UserMapper();
    }

    @Override
    public User addUser(User user) {
        // TODO Auto-generated method stub
        Boolean existsName = userRepository.existsByName(user.getName());
        if (existsName) {
            throw new BadRequestException(
                    "Name " + user.getName() + " taken");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


}
