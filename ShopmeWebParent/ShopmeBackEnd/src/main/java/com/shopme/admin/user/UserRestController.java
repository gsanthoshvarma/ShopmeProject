package com.shopme.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.LongArraySerializer;

@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;

	public UserRestController() {
	}
	
	@PostMapping("/users/checkUniqueEmailId")
	public String checkEmailUnique(@Param("userId") String userId ,@Param("email") String email) {
		boolean isUnique = userService.isEmailUnique(email,Long.valueOf(userId).longValue());
		return isUnique ? "OK" : "Duplicate";
	}

}
