package com.example.service;

import java.util.List;

import com.example.dto.UserDto;
import com.example.model.User;

public interface UserService {
	
	User save(UserDto userDto);
	List<User> findAll();
}
