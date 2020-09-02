package com.cos.opgg.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.opgg.config.handler.exception.MyJoinException;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public RespDto<?> join(Map<String, Object> data) {
		
		if(data.get("nickname") == null || 
				data.get("nickname").equals("") ||
				data.get("password") == null || 
				data.get("password").equals("") || 
				data.get("email") == null || 
				data.get("email").equals("")) {
			System.out.println("값이 입력되지 않음");
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "nickname이나 email이나 password필드가 없습니다.", null);
		}
		
		User userEntity = userRepository.findByEmail((String)data.get("email"));
		
		if(userEntity != null) {
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "해당 이메일로 이미 가입되어 있습니다.", null);
		}
		
		User userRequest = User.builder()
				.username("common_" +(String)data.get("email"))
				.nickname((String)data.get("nickname"))
				.password(bCryptPasswordEncoder.encode((String)data.get("password")))
				.email((String)data.get("email"))
				.provider("common")
				.providerId((String)data.get("email"))
				.roles("ROLE_USER")
				.build();
		
		
		try {
			userRepository.save(userRequest);			
		} catch (Exception e) {
			throw new MyJoinException();
		}
					
		return new RespDto<String>(HttpStatus.OK.value(), "정상", null);
	}

}
