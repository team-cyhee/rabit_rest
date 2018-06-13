package com.cyhee.rabit.user.service;

import com.cyhee.rabit.user.exception.MalformedUserException;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;

public interface UserService {
	Iterable<User> getAllUsers();
	
	void addUser(User user) throws MalformedUserException;
	
	User getUser(Long id) throws NoSuchUserException;
	
	void updateUser(Long id, User userForm) throws NoSuchUserException;
	
	void deleteUser(Long id) throws NoSuchUserException;
	
	User getUserByUsername(String username) throws NoSuchUserException;
	
	User getUserByEmail(String email) throws NoSuchUserException;
}
