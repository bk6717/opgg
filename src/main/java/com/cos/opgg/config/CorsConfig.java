package com.cos.opgg.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
	   System.out.println("oauth.provider.corsFilter.java에 왔습니다");
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.addAllowedOrigin("*"); // Access-Control-Allow-Origin  (Response에 자동으로 추가해줌)
      config.addAllowedHeader("*");  // Access-Control-Request-Headers
      config.addAllowedMethod("*"); // Access-Control-Request-Method

      source.registerCorsConfiguration("/**", config);
	   System.out.println("oauth.provider.corsFilter.java 끝!");
      return new CorsFilter(source);
   }

}
