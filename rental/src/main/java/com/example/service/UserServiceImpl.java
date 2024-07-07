package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.respository.UserRespository;

@Service("UserService")
public class UserServiceImpl implements UserService {	
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRespository userRespository;


	@Override
	public User save(UserDto userDto) {
		User user = new User(null, userDto.getName(),userDto.getEmail(),userDto.getPassword(),userDto.getRole()); 
		return userRespository.save(user);
	}
	
	@Override
	public List<User> findAll() {
		return userRespository.findAll();
	}
	
	

}
