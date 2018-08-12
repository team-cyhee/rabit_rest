package com.cyhee.rabit.user.exception;

import com.cyhee.rabit.cmm.web.exception.ApiException;

@SuppressWarnings("serial")
public abstract class UserException extends RuntimeException implements ApiException {
	public UserException(String message) {
		super(message);
	}
}
