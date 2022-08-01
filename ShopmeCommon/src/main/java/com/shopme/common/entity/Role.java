/**
 * 
 */
package com.shopme.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author GSV
 *
 */
@Entity
@Table(name = "ROLES")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * @GeneratedValue(strategy=GenerationType.AUTO,generator = "sequenceGen")
	 * 
	 * @SequenceGenerator(name = "sequenceGen",sequenceName =
	 * "ROLE_SEQ",allocationSize = 1)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "name",nullable = false,unique = true)
	private String name;
	@Column(name = "description")
	private String desc;
	
	@ManyToMany
	@JoinTable(name = "USER_ROLE",
	      joinColumns = {@JoinColumn(name="role_id")}
	     ,inverseJoinColumns = {@JoinColumn(name="user_id")} )
	private List<User> users = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Role() {
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	

}
