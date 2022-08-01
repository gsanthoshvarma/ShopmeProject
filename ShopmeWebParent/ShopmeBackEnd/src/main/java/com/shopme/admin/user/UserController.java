package com.shopme.admin.user;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.util.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;



@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/users")
	public String getUsers(Model model) {
	   return listUserByPage(1, "firstname","asc","",model);
	}
	
	
	@GetMapping("/users/page/{pageNumber}")
	public String listUserByPage(@PathVariable("pageNumber") int pageNumber, @Param("sortField") 
	String sortField,@Param("sortDir") String sortDir,@Param("keyword") String keyword, Model model) {
	   Page<User> page	= userService.userListByPage(pageNumber,sortField,sortDir,keyword);
	   long startPosition = (pageNumber - 1) * UserService.USER_PAGE_SIZE + 1;
	   long endPosition = pageNumber  * UserService.USER_PAGE_SIZE;
	   String reverseDir = sortDir.equalsIgnoreCase("asc") ? "desc" : "asc";
	   model.addAttribute("users",page.getContent());
	   model.addAttribute("currentPage",pageNumber);
	   model.addAttribute("lastPage",page.getTotalPages());
	   model.addAttribute("startPosition",startPosition);
	   model.addAttribute("endPosition",endPosition);
	   model.addAttribute("sortField",sortField);
	   model.addAttribute("sortDir",sortDir);
	   model.addAttribute("totalRecords",page.getTotalElements());
	   model.addAttribute("reverseDir",reverseDir);
	   return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user = new User();
		user.setEnabled(Boolean.TRUE);
		List<Role> roles = userService.roleList();
		model.addAttribute("roles",roles);
		model.addAttribute("user",user);
		model.addAttribute("PageTitle","Create new user");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(@RequestParam("image") MultipartFile multipartFile, User user,
			RedirectAttributes redirectAttributes) {
		// New user creation
		if(user.getUserId() == 0l) {
			User existUser = userService.saveUser(user);
			if(user.getPhotos() != null) uploadPhoto(multipartFile, user,existUser.getUserId());
			redirectAttributes.addFlashAttribute("message", "User has been created");
			return "redirect:/users";	
		}else {
			//Update user block
			User updatedUser = userService.fetchUserById(user.getUserId());
			if(user.getPassword().isEmpty()) {
				user.setPassword(updatedUser.getPassword());
			}else {
				passwordEncoder(user);
			}
			userService.saveUser(user);
			return "redirect:/users";	
		}
	}

	private void uploadPhoto(MultipartFile multipartFile, User user,long id) {
		String filename = multipartFile.getOriginalFilename();
		user.setPhotos(filename);
		try {
			FileUploadUtil.FileUpload(filename, "user-photos/"+id, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("users/edit/{id}")
	public String updateUser(@PathVariable("id") long id,Model model,RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getUser(id);
			model.addAttribute("user",user);
			List<Role> roles = userService.roleList();
			model.addAttribute("roles",roles);
			model.addAttribute("PageTitle","Edit user");
			return "user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message",e.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("users/delete/{id}")
	public String deleteUser(@PathVariable("id") long id,RedirectAttributes redirectAttributes) {
		 userService.deleteUser(id);
		 redirectAttributes.addFlashAttribute("deleteMsg","User has been deleted");
		 return "redirect:/users";
	}
	
	private User passwordEncoder(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		return user;
	}
	
	
	public UserController() {
		super();
	}
	
	
	
		public static void main(String[] args) {
	 
			try {
				ResourceBundle crunchifyResourceBundle = ResourceBundle.getBundle("com.shopme.common.constant.application");
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
	
	
}
