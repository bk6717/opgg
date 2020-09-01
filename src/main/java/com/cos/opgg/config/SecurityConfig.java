package com.cos.opgg.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.opgg.config.jwt.JwtAuthenticationFilter;
import com.cos.opgg.config.jwt.JwtAuthorizationFilter;
import com.cos.opgg.repository.UserRepository;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter{	
	
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
		http
				.addFilter(corsConfig.corsFilter())
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.formLogin().disable()
				.httpBasic().disable()
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
				.authorizeRequests()

//				.antMatchers("/user/**")
//				.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//				.antMatchers("/manager/**")
//					.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//				.antMatchers("/admin/**")
//					.access("hasRole('ROLE_ADMIN')")

//				.antMatchers("/post/writeProc").authenticated()
//				.antMatchers("/post/update").authenticated()
//				.antMatchers("/post/delete/**").authenticated()
//				.antMatchers("/reply/writeProc").authenticated()
//				.antMatchers("/reply/updateProc").authenticated()
//				.antMatchers("/reply/delete/**").authenticated()
//				.antMatchers("/user/**")
//					.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//				.antMatchers("/manager/**")
//					.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//				.antMatchers("/admin/**")
//					.access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll();
		///흠.,....
	}
}






