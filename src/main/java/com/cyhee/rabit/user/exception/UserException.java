package com.cyhee.rabit.user.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.web.model.ApiErrorCode;

@SuppressWarnings("serial")
public abstract class UserException extends RuntimeException {
	public UserException(String message) {
		super(message);
	}

	public abstract ApiErrorCode getApiErrorCode();
	
	public abstract HttpStatus getStatus();
}
