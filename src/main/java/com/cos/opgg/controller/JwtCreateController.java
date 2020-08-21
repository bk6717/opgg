package com.cos.opgg.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.opgg.config.jwt.JwtProperties;
import com.cos.opgg.config.oauth.provider.CommonUser;
import com.cos.opgg.config.oauth.provider.GoogleUser;
import com.cos.opgg.config.oauth.provider.OAuthUserInfo;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/oauth/jwt/common")
	public String commonlogin(@RequestBody Map<String, Object> data) {
		System.out.println("controller.JwtCreateController.java의 jwtCreate에 왔습니다 ");
		System.out.println("여긴 데이터 data = "+data);
		CommonUser commonUser =
				new CommonUser((Map<String, Object>)data);
		System.out.println("------------------------------------------------------------");
		System.out.println("googleUser.getProvider() = "+commonUser.getProvider());
		System.out.println("googleUser.getProviderId() = "+commonUser.getProviderId());
		User userEntity =
				userRepository.findByUsername(commonUser.getName());
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 userEntity = "+userEntity);
		if(userEntity == null) {

			System.out.println("아이디가 없습니다, 회원가입으로 이동");
			return "아이디없음"; // 인증실패시 회원가입페이지등으로 안내

		}
		
		if(bCryptPasswordEncoder.matches(commonUser.getPassword(), userEntity.getPassword())) {
			return "비번틀림"; // 아이디는 있으나 비밀번호가 틀림
		}

		String jwtToken = JWT.create()
				.withSubject(userEntity.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", userEntity.getId())
				.withClaim("username", userEntity.getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 jwtToken = "+jwtToken);

		return jwtToken;
	}

	@PostMapping("/oauth/jwt/google")
	public String jwtCreate(@RequestBody Map<String, Object> data) {
		System.out.println("controller.JwtCreateController.java의 jwtCreate에 왔습니다 ");
		System.out.println("여긴 데이터 data = "+data);
		System.out.println(data.get("profileObj"));//구글에서 주는양식 .
		OAuthUserInfo googleUser =
				new GoogleUser((Map<String, Object>)data.get("profileObj"));
		System.out.println("------------------------------------------------------------");
		System.out.println("googleUser.getProvider() = "+googleUser.getProvider());
		System.out.println("googleUser.getProvider() = "+googleUser.getProviderId());
		User userEntity =
				userRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 userEntity = "+userEntity);
		if(userEntity == null) {
			System.out.println("controller.JwtCreateController.java의 jwtCreate의 if(userEntity == null)에 왔습니다 ");


			User userRequest = User.builder()
					.username(googleUser.getProvider()+"_"+googleUser.getProviderId())
					 .password(bCryptPasswordEncoder.encode("겟인데어"))
					.email(googleUser.getEmail())
					.provider(googleUser.getProvider())
					.providerId(googleUser.getProviderId())
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

		return jwtToken;
	}

}
