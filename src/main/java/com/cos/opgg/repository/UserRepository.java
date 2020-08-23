package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	 User findByUsername(String username);
	 
}
