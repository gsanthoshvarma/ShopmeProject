package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	
	@Test
	public void testUserCreationWithSingleRole() {
		User user = new User();
		user.setFirstname("Santhosh");
		user.setLastname("Gottimukkula");
		user.setEmail("santhoshvarma.g@gmail.com");
		user.setPassword("Welcome123$");
		user.setEnabled(true);
		
		//Admin Role
		Role adminRole = testEntityManager.find(Role.class,2l);
		user.getRoles().add(adminRole);
		userRepository.save(user);
	}
	
	@Test
	public void testUserCreationWithMultipleRoles() {
		User user = new User();
		user.setFirstname("Guest");
		user.setLastname("Guest1");
		user.setEmail("Guest.g@gmail.com");
		user.setPassword("Welcome123$");
		user.setEnabled(true);
		
		//Sales Person Role
		Role salesPersonRole = testEntityManager.find(Role.class, 3l);
		user.getRoles().add(salesPersonRole);
		
		//Sales Person Role
		Role shipperRole = testEntityManager.find(Role.class, 5l);
		user.getRoles().add(shipperRole);
				
		userRepository.save(user);
	}
	
	@Test
	public void testFetchUsers() {
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepository.findAll(pageable);
		page.getContent().forEach(user -> System.out.println(user.getFirstname()));
		assertEquals(0, page.getPageable().getPageNumber());
		assertEquals(4, page.getPageable().getPageSize());
		assertNotNull(page.getContent());
		assertThat((page.getContent().stream().count())).isGreaterThan(0);
	}
	
	@Test
	public void testFindByEmail() {
		Optional<User> user = userRepository.findByEmail("santhoshvarma.g@gmail.com");
		assertNotNull(user);
		
	}
	
}
