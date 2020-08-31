package com.cos.opgg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.dto.RespDto;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping({"","/"})
	public RespDto<?> userTest() {
		return new RespDto<String>(HttpStatus.OK.value(),"정상","회원가입 및 로그인 성공");
	}
}
