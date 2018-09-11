package com.cyhee.rabit.exception.user;

import com.cyhee.rabit.web.cmm.exception.ApiException;

@SuppressWarnings("serial")
public abstract class UserException extends RuntimeException implements ApiException {
	public UserException(String message) {
		super(message);
	}
}
