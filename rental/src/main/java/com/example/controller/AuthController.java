package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.JwtProvider;
import com.example.model.User;
import com.example.request.LoginRequest;
import com.example.response.AuthResponse;
import com.example.respository.UserRespository;
import com.example.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRespository userRespository;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetails;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public AuthResponse createUser(@RequestBody User user)throws Exception{
		
		String name=user.getName();
		String email=user.getEmail();
		String password=user.getPassword();
		
		
		
		User isExistEmail=userRespository.findByEmail(email);
		if(isExistEmail!=null) {
			throw new Exception("Email is already used");
		}
		
		User createdUser=new User();
		createdUser.setName(name);
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		User savedUser=userRespository.save(createdUser);
		Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse();
		
		res.setJwt(token);
		res.setMessage("signup success");
		
		return res;
	}
	
	@PostMapping("/login")
	public AuthResponse loginHandler(@RequestBody LoginRequest loginRequest) {
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		Authentication authentication=authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse();
		
		res.setJwt(token);
		res.setMessage("signup success");
		return res;
	}

	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails=customerUserDetails.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("user not found");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}

}
