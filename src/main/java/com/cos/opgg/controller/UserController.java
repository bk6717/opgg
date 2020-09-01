package com.cos.opgg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.dto.RespDto;
import com.cos.opgg.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({ "", "/" })
	public RespDto<?> userTest() {
		return new RespDto<String>(HttpStatus.OK.value(), "정상", "회원가입 및 로그인 성공");
	}
	
	//회원가입
	@PostMapping("join")
	public RespDto<?> join(@RequestBody Map<String, Object> data) {
		
		return userService.join(data);
	}
}
