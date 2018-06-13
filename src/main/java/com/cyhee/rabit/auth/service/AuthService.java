package com.cyhee.rabit.auth.service;

import com.cyhee.rabit.user.exception.NoSuchUserException;

public interface AuthService {
	
	boolean check(String username, String password) throws NoSuchUserException;
}
