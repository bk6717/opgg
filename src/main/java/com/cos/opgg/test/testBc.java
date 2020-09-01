package com.cos.opgg.test;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class testBc {
	
	
	public void bc() {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		System.out.println(bCryptPasswordEncoder.encode("123"));
		
	}

}
