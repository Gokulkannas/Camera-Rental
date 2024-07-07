package com.example.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
	User findByEmail(String email); 

}
