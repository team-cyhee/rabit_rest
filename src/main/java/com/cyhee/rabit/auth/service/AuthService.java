package com.cyhee.rabit.auth.service;

import org.springframework.web.client.RestClientException;

import com.cyhee.rabit.user.exception.NoSuchUserException;

public interface AuthService {
	
	boolean check(String username, String password) throws NoSuchUserException;
	
	String getToken(String username, String password) throws RestClientException;
}
