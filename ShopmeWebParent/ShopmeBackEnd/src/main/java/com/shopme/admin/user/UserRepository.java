package com.shopme.admin.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	
	public Optional<User> findByEmail(String email);
	
	@Query("SELECT u from User u where u.firstname LIKE %?1% OR u.lastname LIKE %?1%")
	public Page<User> findAll(String keyword,Pageable pageable);

}
