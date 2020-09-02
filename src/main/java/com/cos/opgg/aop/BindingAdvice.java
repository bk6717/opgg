package com.cos.opgg.aop;


import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.opgg.dto.RespDto;


//공통관심사 : advice  => 관심사로 분리하고 핵심 비즈니스 로직만들 작성
@Component //모든 어노테이션은 component의 자식이다.
@Aspect  //Aop로 등록이된다. 
public class BindingAdvice {
	
//	@Before("execution(* com.cos.validex01.test.BindControllerTest.*(..))")
//	public void test1() {
//		System.out.println("BindController에 오신것을 환영합니다.");
//	}
//	
//	@After("execution(* com.cos.validex01.test.BindControllerTest.*(..))")
//	public void test2() {
//		System.out.println("BindController에 오신것을 환영합니다.");
//	}
	
	//@Before, @After, @Around 
	@Around("execution(* com.cos.opgg..*Controller.*(..))")
	//joinPoint에 접근하기위해사용 => bindResult에 접근할수 있다.
	//proceedingJoinPoint를 가져올수 있다 (메서드의 컨텍스트)
	//모든리턴타입을 가진 validex01 이하에있는 모든패키지에있는 Controller 의 모든 파라메터
	public Object validationHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();  // 실행되는 타입
		String method = proceedingJoinPoint.getSignature().getName(); //실행되는 메서드 명
		
		System.out.println("type : " + type);
		System.out.println("method : " + method);
		
		Object[] args = proceedingJoinPoint.getArgs(); // 조인 포인트의 파라메터
		
		for (Object arg : args) {
			System.out.println("BindingAdvice : for(Object arg_) : 진입");
			System.out.println("BindingAdvice arg : "+arg);
			if (arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if (bindingResult.hasErrors()) {
					System.out.println("에러발생 hassError() ");
					//오류들의 메시지만 담으려고 만든다.
					Map<String, String> errorMap = new HashMap<>();
					
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					
					RespDto<?> respDto = RespDto.builder()
							.statusCode(HttpStatus.BAD_REQUEST.value())
							.message(method + " 요청에 실패하였습니다.")
							.data(errorMap)
							.build();
					
					return new ResponseEntity<RespDto>(respDto, HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		System.out.println("BindingAdvice : 종료 ===========================================");
		System.out.println();
		return proceedingJoinPoint.proceed(); //다시 핵심로직
	}
}