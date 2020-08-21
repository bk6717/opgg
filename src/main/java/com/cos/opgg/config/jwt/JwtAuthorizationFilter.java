package com.cos.opgg.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.opgg.config.auth.PrincipalDetails;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.UserRepository;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		System.err.println("jwt.JwtAuthorizationFilter.java의 JwtAuthorizationFilter에 왔습니다");
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal에 왔습니다");
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 header= " + header);

		if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 header == null에 도착 ");
			chain.doFilter(request, response);
			System.err.println("---------------------終わりました---------------------------");
			return;

		}

		String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
		System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 token ="+token);
		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
		// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
		// loadByUsername이 호출됨.
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("username").asString();
		System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 username ="+username);
		if (username != null) {
			System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 username != null 에 도착");
			User user = userRepository.findByUsername(username);
			System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 user = "+user);

			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 principalDetails = "+principalDetails);

			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서
																									// 쓸 때 사용하기 편함.
					null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
					principalDetails.getAuthorities());

			// 강제로 시큐리티의 세션에 접근하여 값 저장
			System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal의 authentication = "+authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		System.err.println("jwt.JwtAuthorizationFilter.java의 doFilterInternal가 끝났습니다 " );
		chain.doFilter(request, response);
	}

}
