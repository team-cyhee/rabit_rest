package com.cyhee.rabit.exception.user;

import com.cyhee.rabit.exception.ApiException;

@SuppressWarnings("serial")
public abstract class UserException extends ApiException {
	public UserException(String message) {
		super(message);
	}
}
