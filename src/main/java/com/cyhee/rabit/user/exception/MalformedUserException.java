package com.cyhee.rabit.user.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.cmm.web.model.ApiErrorCode;

@SuppressWarnings("serial")
public class MalformedUserException extends UserException {
	public MalformedUserException() {
		super("Invalid User input type!");
	}
	
	public MalformedUserException(String msg) {
		super(msg);
	}

	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.INVALID_INPUT_TYPE;
	}

	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
