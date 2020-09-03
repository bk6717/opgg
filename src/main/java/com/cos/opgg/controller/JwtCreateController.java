package com.cos.opgg.controller;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.opgg.config.jwt.JwtProperties;
import com.cos.opgg.config.oauth.provider.CommonUser;
import com.cos.opgg.config.oauth.provider.GoogleUser;
import com.cos.opgg.config.oauth.provider.KakaoUser;
import com.cos.opgg.config.oauth.provider.OAuthUserInfo;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.dto.TokenDto;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// jwt로 로그인 하기위한 컨트롤러

@RestController
@RequiredArgsConstructor
public class JwtCreateController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// 일반로그인
	@PostMapping("jwt/common")
	public RespDto<?> commonlogin(@RequestBody Map<String, Object> data, HttpServletResponse response) {
		System.out.println("controller.JwtCreateController.java의 jwtCreate에 왔습니다 ");
		System.out.println("여긴 데이터 data = "+data);
		if(data.get("email") == null || data.get("email").equals("") || data.get("password") == null || data.get("password").equals("")) {
			System.out.println("값이 입력되지 않음");
			return new RespDto<TokenDto>(HttpStatus.BAD_REQUEST.value(), "email이나 password필드가 없습니다.", null);
		} else if(
				//정규표현식 http://emailregex.com/
			!((String)data.get("email")).matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
			) {
			return new RespDto<TokenDto>(HttpStatus.BAD_REQUEST.value(), "email이 형식에 맞지 않습니다.", null);
		}
		
		
		CommonUser commonUser =
				new CommonUser((Map<String, Object>)data);
		System.out.println("googleUser.getProvider() = "+commonUser.getProvider());
		System.out.println("googleUser.getProviderId() = "+commonUser.getProviderId());
		User userEntity =
				userRepository.findByUsername(commonUser.getProvider()+"_"+commonUser.getProviderId());
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 userEntity = "+userEntity);
		if(userEntity == null) {

			System.out.println("아이디가 없습니다, 회원가입으로 이동요망");
			return new RespDto<TokenDto>(HttpStatus.UNAUTHORIZED.value(), "해당 아이디가 없습니다.", null);

		}
		
		if(!bCryptPasswordEncoder.matches(commonUser.getPassword(), userEntity.getPassword())) {
			System.out.println("비번틀림");
			return new RespDto<TokenDto>(HttpStatus.UNAUTHORIZED.value(), "비밀번호가 틀렸습니다.", null);
		}

		String jwtToken = JWT.create()
				.withSubject(userEntity.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", userEntity.getId())
				.withClaim("username", userEntity.getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 jwtToken = "+jwtToken);
		System.out.println("정상");
		
		TokenDto tokenDto = TokenDto.builder()
				.userId(userEntity.getId())
				.nickname(userEntity.getNickname())
				.jwtToken(jwtToken)
				.build();
		
		// 어드민일 경우 쿠키에 담는다 (jsp 페이지 이기 때문)
		if(userEntity.getRoles().equals("ROLE_ADMIN")) {
			
			Cookie cookie = new Cookie("jwtToken", tokenDto.getJwtToken());
			cookie.setMaxAge(600);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
			System.out.println("쿠키에 토큰을 담았습니다.");
			
		}
				
		return new RespDto<TokenDto>(HttpStatus.OK.value(), "정상", tokenDto);
	}
	

	// 구글 카카오
	@PostMapping("jwt/oauth")
	public RespDto<?> jwtCreate(@RequestBody Map<String, Object> data) {
		
		// accessToken으로 검증(생략)
		
		System.out.println(data);
		
		if (data.get("kakaoId") == null && data.get("googleId") == null) {
			return new RespDto<Map>(HttpStatus.BAD_REQUEST.value(), "에러가 발생하였습니다.", data);
		}
		
		if(data.get("email") == null || data.get("email").equals("") || data.get("name") == null || data.get("name").equals("")) {
			System.out.println("값이 입력되지 않음");
			return new RespDto<Map>(HttpStatus.BAD_REQUEST.value(), "email이나 name필드가 없습니다.", null);
		} else if(
				//정규표현식 http://emailregex.com/
			!((String)data.get("email")).matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
			) {
			return new RespDto<Map>(HttpStatus.BAD_REQUEST.value(), "email이 형식에 맞지 않습니다.", null);
		}
		
		System.out.println("controller.JwtCreateController.java의 jwtCreate에 왔습니다 ");
		System.out.println("여긴 데이터 data = "+data);
		
		OAuthUserInfo oAuthUserinfo = null;
		
		if (data.get("googleId") != null) {
			oAuthUserinfo = new GoogleUser(data);
		} else if (data.get("kakaoId") != null) {
			oAuthUserinfo = new KakaoUser(data);
		}
		
		System.out.println("------------------------------------------------------------");
		System.out.println("googleUser.getProvider() = "+oAuthUserinfo.getProvider());
		System.out.println("googleUser.getProvider() = "+oAuthUserinfo.getProviderId());
		User userEntity =
				userRepository.findByUsername(oAuthUserinfo.getProvider()+"_"+oAuthUserinfo.getProviderId());
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 userEntity = "+userEntity);
		if(userEntity == null) {
			System.out.println("controller.JwtCreateController.java의 jwtCreate의 if(userEntity == null)에 왔습니다 ");


			User userRequest = User.builder()
					.username(oAuthUserinfo.getProvider()+"_"+oAuthUserinfo.getProviderId())
					.nickname("opgg_" + UUID.randomUUID().toString().substring(0,13))
					.password(bCryptPasswordEncoder.encode("겟인데어"))
					.email(oAuthUserinfo.getEmail())
					.provider(oAuthUserinfo.getProvider())
					.providerId(oAuthUserinfo.getProviderId())
					.roles("ROLE_USER")
					.build();
			
			userEntity = userRepository.save(userRequest);

			System.out.println("controller.JwtCreateController.java의 jwtCreate의 if(userEntity == null)의 userEntity = "+userEntity);
		}

		String jwtToken = JWT.create()
				.withSubject(userEntity.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", userEntity.getId())
				.withClaim("username", userEntity.getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 jwtToken = "+jwtToken);
		
		
		TokenDto tokenDto = TokenDto.builder()
				.userId(userEntity.getId())
				.nickname(userEntity.getNickname())
				.jwtToken(jwtToken)
				.build();
				
		
		return new RespDto<TokenDto>(HttpStatus.OK.value(), "정상", tokenDto);
	}

}
