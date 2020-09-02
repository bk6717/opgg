package com.cos.opgg.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.config.handler.exception.MyJoinException;
import com.cos.opgg.config.handler.exception.MyJwtErrorException;
import com.cos.opgg.config.handler.exception.MyPostWriteException;
import com.cos.opgg.dto.RespDto;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value=MyJoinException.class)
	public RespDto<String> join(Exception e) {
		return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "닉네임이 중복입니다.", null);
	}
	
	@ExceptionHandler(value=MyPostWriteException.class)
	public RespDto<String> writePost(Exception e) {
		return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "글 등록에 실패하였습니다.", null);
	}
	
	@ExceptionHandler(value=MyJwtErrorException.class)
	public RespDto<String> jwtError(Exception e) {
		return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "토큰에 문제가 있습니다. 재발급 요망", null);
	}
	
	@ExceptionHandler(value=UsernameNotFoundException.class)
	public RespDto<String> detailsService(Exception e) {
		return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "해당 유저네임이 없습니다.", null);
	}
	
	
}