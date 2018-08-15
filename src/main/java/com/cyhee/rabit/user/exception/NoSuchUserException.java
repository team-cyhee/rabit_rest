package com.cyhee.rabit.user.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.cmm.web.exception.NoSuchContentException;
import com.cyhee.rabit.cmm.web.model.ApiErrorCode;

/**
 * @author chy
 * use {@link NoSuchContentException}
 */
@Deprecated
@SuppressWarnings("serial")
public class NoSuchUserException extends UserException{
	public NoSuchUserException(long id) {
		super(String.format("No such user with id '%d'",id));
	}
	
	public NoSuchUserException(String username) {
		super(String.format("No such user with username '%s'", username));
	}

	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.INVALID_INPUT_TYPE;
	}

	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
