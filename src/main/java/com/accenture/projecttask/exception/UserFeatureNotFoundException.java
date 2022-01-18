package com.accenture.projecttask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserFeatureNotFoundException extends RuntimeException{

    public UserFeatureNotFoundException(String msg) {
        super(msg);
    }
}
