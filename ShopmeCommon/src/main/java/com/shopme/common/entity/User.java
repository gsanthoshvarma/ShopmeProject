package com.shopme.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private long userId;
	
	@Column(name = "email",length = 100,nullable = false, unique = true)
	private String email;
	
	@Column(name = "password",length = 64,nullable = false)
	private String password;
	
	@Column(name = "first_name",length = 50,nullable = false)
	private String firstname;
	
	@Column(name = "last_name",length = 50,nullable = false)
	private String lastname;
	
	@Column(name = "photos",length = 64)
	private String photos;
	
	@Column(name = "enabled",length = 100)
	private boolean enabled;
	
	
	@ManyToMany
	@JoinTable(name = "USER_ROLE", joinColumns = {@JoinColumn(name="user_id")} ,
	    inverseJoinColumns = {@JoinColumn(name="role_id")} )
	private List<Role> roles = new ArrayList<>();
	 
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getPhotoImagePath() {
		if(userId == 0l || photos == null) {
			return "/images/default_user.png";
		}else {
			return "/user-photos/" +this.userId +"/"+this.photos; 
		}
	}

	public User() {
	}
}
