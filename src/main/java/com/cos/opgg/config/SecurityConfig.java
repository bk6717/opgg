package com.cos.opgg.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.savedrequest.RequestCache;

import com.cos.opgg.config.jwt.JwtAuthenticationFilter;
import com.cos.opgg.config.jwt.JwtAuthorizationFilter;
import com.cos.opgg.repository.UserRepository;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CorsConfig corsConfig;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		System.out.println("jwt.SecurityConfig의 passwordEncoder에 왔습니다");
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilter(corsConfig.corsFilter()).csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.formLogin().disable().httpBasic().disable()
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
				// 인증이 필요한 페이지
				.authorizeRequests()
				.antMatchers("/post/writeProc")
				.authenticated().antMatchers("/post/update")
				.authenticated().antMatchers("/post/delete/**")
				.authenticated().antMatchers("/reply/writeProc")
				.authenticated().antMatchers("/reply/updateProc")
				.authenticated().antMatchers("/reply/delete/**")
				.authenticated()
				// 인가가 필요한 페이지
				// 관리자는 쿠키로 jwt를 전송하여 인가
				.antMatchers("/user/**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/manager/**")
				.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/user/**")
				.access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/reply/**")
				.access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/post/**")
				.access("hasRole('ROLE_ADMIN')")
				// 나머지는 허용
				.anyRequest().permitAll();

//		http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
//			@Override
//			public void commence(HttpServletRequest request, HttpServletResponse response,
//					AuthenticationException authException) throws IOException, ServletException {
//				response.sendRedirect("/admin/login");
//			}
//		}).accessDeniedHandler(new AccessDeniedHandler() {
//			@Override
//			public void handle(HttpServletRequest request, HttpServletResponse response,
//					AccessDeniedException accessDeniedException) throws IOException, ServletException {
//				response.sendRedirect("/admin/login");
//			}
//		});
		
	}
}
