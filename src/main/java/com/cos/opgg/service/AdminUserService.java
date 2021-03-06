package com.cos.opgg.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.opgg.model.User;
import com.cos.opgg.repository.PostRepository;
import com.cos.opgg.repository.ReplyRepository;
import com.cos.opgg.repository.UserRepository;

@Service
public class AdminUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void updateRole(Map<String, String> roles, int id) {
		User userEntity = userRepository.findById(id).get();
		userEntity.setRoles(roles.get("role"));
	}
	
	@Transactional
	public void deleteUser(int id) {
		
		replyRepository.deleteByUserId(id);
		postRepository.deleteByUserId(id);
		userRepository.deleteById(id);
		
	}
	
}
