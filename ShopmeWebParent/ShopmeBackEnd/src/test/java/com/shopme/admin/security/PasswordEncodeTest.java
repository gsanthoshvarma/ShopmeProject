package com.shopme.admin.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeTest {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test 
	public void testPasswordEncoded() {
		String encodedPwd = passwordEncoder.encode("Welcome123$");
		passwordEncoder.matches(encodedPwd, "Welcome123$");
	}
	public PasswordEncodeTest() {
	}

}
