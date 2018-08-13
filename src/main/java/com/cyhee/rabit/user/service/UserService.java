package com.cyhee.rabit.user.service;

import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.user.exception.MalformedUserException;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;

public interface UserService {	
	Iterable<User> getUsers(Pageable pageable);
	
	void addUser(User user) throws MalformedUserException;
	
	User getUser(Long id) throws NoSuchUserException;
	
	User getUserByUsername(String username) throws NoSuchUserException;
	
	void updateUser(Long id, User userForm) throws NoSuchUserException;
	
	void updateUserByUsername(String username, User userForm) throws NoSuchUserException;
	
	void deleteUser(Long id) throws NoSuchUserException;
	
	void deleteUserByUsername(String username) throws NoSuchUserException;
}
