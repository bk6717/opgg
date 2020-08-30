package com.cos.opgg.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class testBc {
	
	@Test
	public void bc() {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		System.out.println(bCryptPasswordEncoder.encode("123"));
		
	}

}
