package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testCreateAdminRole() {
		//Admin
		Role admin = new Role();
		admin.setName("Admin");
		admin.setDesc("Manage everything");
		roleRepository.save(admin);
	}
	
	@Test
	public void testCreateOtherRoles() {
		//Salesperson creation
		Role saleperson = new Role();
		saleperson.setName("Salesperson");
		saleperson.setDesc("Manage product price,customers, shopping,Order and sales report");
		roleRepository.save(saleperson);
		
		//Editor
		Role editor = new Role();
		editor.setName("Editor");
		editor.setDesc("Manage Categories,brands,products,articles and menu");
		roleRepository.save(editor);
		
		//Shipper
		Role shipper = new Role();
		shipper.setName("Shipper");
		shipper.setDesc("View products,view order and update order status");
		roleRepository.save(shipper);
		
		//Assistant
		Role assistant = new Role();
		assistant.setName("Assistant");
		assistant.setDesc("Manage question and reviews");
		roleRepository.save(assistant);
	}
	
	@Test
	public void fetchAllRoles() {
		List<Role> roles = (List<Role>)roleRepository.findAll();
		assertNotNull(roles);
		assertThat(roles.stream().count()).isGreaterThan(0);
	}
	
	
	public RoleRepositoryTest() {
		super();
	}

}
