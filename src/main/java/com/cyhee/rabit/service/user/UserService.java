package com.cyhee.rabit.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.model.user.User;

public interface UserService {	
	Page<User> getUsers(Pageable pageable);
	
	void addUser(User user);
	
	User getUser(Long id);
	
	User getUserByUsername(String username);
	
	void updateUser(Long id, User userForm);
	
	void updateUserByUsername(String username, User userForm);
	
	void deleteUser(Long id, Pageable pageable);
	
	void deleteUserByUsername(String username, Pageable pageable);
}
