package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.respository.UserRespository;
import com.example.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRespository userRespository;
		
	@GetMapping("/user")
	public List<User> getAllUsers() {
		return userService.findAll();
	}
	 
	@PostMapping("/user")
	public User postUsers(@RequestBody UserDto userDto) throws Exception {
		User isExist=userRespository.findByEmail(userDto.getEmail());
		if(isExist!=null) {
			throw new Exception("email is already registered");
		}
		return userService.save(userDto);
	}
}
