package com.cos.opgg.controller;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
import com.cos.opgg.config.oauth.provider.OAuthUserInfo;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/oauth/jwt/common")
	public RespDto<?> commonlogin(@RequestBody Map<String, Object> data) {
		System.out.println("controller.JwtCreateController.java의 jwtCreate에 왔습니다 ");
		System.out.println("여긴 데이터 data = "+data);
		if(data.get("email") == null || data.get("password") == null) {
			System.out.println("값이 입력되지 않음");
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "email이나 password필드가 없습니다.", null);
		}
		CommonUser commonUser =
				new CommonUser((Map<String, Object>)data);
		System.out.println("googleUser.getProvider() = "+commonUser.getProvider());
		System.out.println("googleUser.getProviderId() = "+commonUser.getProviderId());
		User userEntity =
				userRepository.findByUsername(commonUser.getProvider()+"_"+commonUser.getProviderId());
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 userEntity = "+userEntity);
		if(userEntity == null) {

			System.out.println("아이디가 없습니다, 회원가입으로 이동");
			return new RespDto<String>(HttpStatus.UNAUTHORIZED.value(), "해당 아이디가 없습니다.", null);

		}
		
		if(!bCryptPasswordEncoder.matches(commonUser.getPassword(), userEntity.getPassword())) {
			System.out.println("비번틀림");
			return new RespDto<String>(HttpStatus.UNAUTHORIZED.value(), "비밀번호가 틀렸습니다.", null);
		}

		String jwtToken = JWT.create()
				.withSubject(userEntity.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", userEntity.getId())
				.withClaim("username", userEntity.getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		System.out.println("controller.JwtCreateController.java의 jwtCreate의 jwtToken = "+jwtToken);
		System.out.println("정상");
		return new RespDto<String>(HttpStatus.OK.value(), "정상", jwtToken);
	}

	@PostMapping("/oauth/jwt/google")
	public RespDto<?> jwtCreate(@RequestBody Map<String, Object> data) {
		
		
		// 포털 서버에 검증
		
//		// Profile 받기 (Resource Server)
//				String endpoint2 = "https://kapi.kakao.com/v2/user/me";
//				URL url2 = new URL(endpoint2);
//				
//				HttpsURLConnection conn2 = (HttpsURLConnection)url2.openConnection();
//				
//				// http header 값 넣기
//				conn2.setRequestProperty("Authorization", "Bearer "+ kakoToken.getAccess_token());
//				conn2.setDoOutput(true);
//				
//				// request 하기
//				BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
//				String input2 = "";
//				StringBuilder sb2 = new StringBuilder();
//				while((input2 = br2.readLine()) != null) {sb2.append(input2);}
//				System.out.println(sb2.toString());
//				
//				// Gson으로 파싱
//				Gson gson2 = new Gson();
//				kakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), kakaoProfile.class);
		
		
		if (data.get("profileObj") == null) {
			return new RespDto<Map>(HttpStatus.BAD_REQUEST.value(), "에러가 발생하였습니다.", data);
		}
		
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
					.nickname("opgg_" + UUID.randomUUID())
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

		return new RespDto<String>(HttpStatus.OK.value(), "정상", jwtToken);
	}

}
