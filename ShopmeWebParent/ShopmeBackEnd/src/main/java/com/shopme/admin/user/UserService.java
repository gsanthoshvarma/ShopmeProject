package com.shopme.admin.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import org.springframework.aop.TargetClassAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
public class UserService {

	public static final int  USER_PAGE_SIZE = 4;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	 
	
	public List<User> userList(){
		
		 return (List<User>) userRepository.findAll();
	}
	
	public Page<User> userListByPage(int pagenumber,String sortField,String sortDir,String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pagenumber - 1 ,USER_PAGE_SIZE,sort);
		if(StringUtils.hasText(keyword)) {
			Page<User> page =  userRepository.findAll(keyword,pageable);	
			return page;
		}else {
			Page<User> page =  userRepository.findAll(pageable);
			return page;
		}
		
		
		
	}
	
	public List<Role> roleList(){
		return (List<Role>) roleRepository.findAll();
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public boolean isEmailUnique(String email,long userId) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isEmpty()) {
			return Boolean.TRUE;
		}else {
			if(user.get().getUserId() == userId) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE ;
	}
	
	public UserService() {
	}
	
	
	public User getUser(long id) throws UserNotFoundException  {
		try {
			User user = userRepository.findById(id).get();
			return user;
		}catch (NoSuchElementException e) {
			throw new UserNotFoundException("Couldn't find any user with ID "+id);
		}
	}
	
	public User fetchUserById(long id) {
	  Optional<User> user =	userRepository.findById(id);
	  return user.get();
	}
	
	public boolean deleteUser(long id) {
		userRepository.deleteById(id);
		return true;
	}
	
	public static void testCode() {
		try {
			ResourceBundle crunchifyResourceBundle = ResourceBundle.getBundle("../test");
			Enumeration<String> crunchifyKeys = crunchifyResourceBundle.getKeys();
			while (crunchifyKeys.hasMoreElements()) {
				String crunchifyKey = crunchifyKeys.nextElement();
				String value = crunchifyResourceBundle.getString(crunchifyKey);
				System.out.println(crunchifyKey + ": " + value);
			}
 
		} catch (Exception e) {
			System.out.println("Error retrieving properties file: " + e);
		}
	}
	
	public static void testCode2() {
			Properties prop = new Properties();
			try {
				prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("com.shopme.admin.test.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			prop.get("shopmeCommon");
	}
	
	public void testCode1() {
		Properties prop = new Properties();
		ClassLoader c1 = this.getClass().getClassLoader();
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(c1);
		try {
			Resource[] resources = patternResolver.getResources("classpath:/datasource/test.properties");
			for(Resource resource : resources) {
				System.out.println(resource.getFilename());
				prop.load(resource.getInputStream());
				System.out.println(prop.get("shopmeCommon"));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	 

}
