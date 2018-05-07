package com.cyhee.rabit.user.service;

import com.cyhee.rabit.user.exception.MalformedUserException;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;

public interface UserService {
	Iterable<User> getAllUsers();
	
	void addUser(User user) throws MalformedUserException;
	
	User getUser(int id) throws NoSuchUserException;
	
	void updateUser(int id, User userForm) throws NoSuchUserException;
	
	void deleteUser(int id) throws NoSuchUserException;
}
