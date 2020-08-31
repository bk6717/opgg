package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cos.opgg.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	 User findByUsername(String username);
	 
	 User findByEmail(String email);
	 
	 @Query(value = "select UserName from user", nativeQuery = true)
	 User findUserName();
//	 
//	 @Query(value = "UPDATE user SET roles = ?1 WHERE id = ?2", nativeQuery = true)
//	 User updateRole(String roles, int id );
	 
	 @Query(value =" select * from user where email like %?1%", nativeQuery = true)
	 List<User> search(String email);
	 

}
