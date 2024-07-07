package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.respository.UserRespository;
@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRespository userRespository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRespository.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new CustomUserDetail(user);
	}

}
