package com.cyhee.rabit.user.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.web.model.ApiErrorCode;

@SuppressWarnings("serial")
public class NoSuchUserException extends UserException{
	public NoSuchUserException() {
		super("No such user id!");
	}

	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.INVALID_INPUT_TYPE;
	}

	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
