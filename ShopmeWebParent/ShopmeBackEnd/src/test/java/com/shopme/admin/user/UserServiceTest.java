package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;


public class UserServiceTest {
	
	private UserService userService;
	private UserRepository userRepository;
	private static final String EMAIL_ID = "Santhoshvarma.g@gmail.com";
	
	@BeforeEach
	public void init() {
		userService = new UserService();
		userRepository = mock(UserRepository.class);
	}
	
	@Test
	public void testSaveUser() {
		User user = buildUser();
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
		when(userService.saveUser(user)).thenAnswer(new Answer<User>() {
			public User answer(InvocationOnMock invocationOnMock) throws Throwable {
				return user;
			}
		});
		User newUser = userService.saveUser(user);
		assertEquals(2l, newUser.getUserId());
		assertEquals("Jhon", newUser.getFirstname());
		assertEquals("Will", newUser.getLastname());
		assertEquals("Jhon.will@deleteme.com", newUser.getEmail());
		assertThat(newUser.getRoles()).hasSizeGreaterThan(0);
		
	}

	private User buildUser() {
		User user = new User();
		user.setUserId(2l);
		user.setFirstname("Jhon");
		user.setLastname("Will");
		user.setPassword("John$");
		user.setEmail("Jhon.will@deleteme.com");
		user.setEnabled(Boolean.TRUE);
		
		Role admin = new Role();
		admin.setId(10l);
		admin.setName("Admin");
		
		user.getRoles().add(admin);
		return user;
	}
	
	@Test
	public void testIsEmailUnique() {
		User user = new User();
		when(userRepository.findByEmail("")).thenReturn(Optional.of(user));
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
		assertEquals(true, userService.isEmailUnique(EMAIL_ID,2l));
	}
	
	@Test
	public void testIsEmailWithDuplicate() {
		userService = new UserService();
		User user = new User();
		user.setEmail(EMAIL_ID);
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
		when(userRepository.findByEmail(EMAIL_ID)).thenReturn(Optional.of(user));
		assertEquals(false, userService.isEmailUnique(EMAIL_ID,1l));
	}
	
	@Test
	public void testIsEmailWithUpdateEmail() {
		userService = new UserService();
		User user = new User();
		user.setUserId(1l);
		user.setEmail(EMAIL_ID);
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
		when(userRepository.findByEmail(EMAIL_ID)).thenReturn(Optional.of(user));
		assertEquals(true, userService.isEmailUnique(EMAIL_ID,1l));
	}

	public UserServiceTest() {
		super();
	}

}
