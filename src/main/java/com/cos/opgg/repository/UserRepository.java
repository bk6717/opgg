package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	 User findByUsername(String username);
	 
	 User findByEmail(String email);
	 
	 @Query(value = "select UserName from user", nativeQuery = true)
	 User findUserName();
	 
}
