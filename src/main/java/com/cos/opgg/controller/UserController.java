package com.cos.opgg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

	// 회원가입
	@PostMapping("join")
	public RespDto<?> join(@RequestBody Map<String, Object> data) {

		return userService.join(data);
	}

}
