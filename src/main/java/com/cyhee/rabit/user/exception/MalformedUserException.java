package com.cyhee.rabit.user.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.web.model.ApiErrorCode;

@SuppressWarnings("serial")
public class MalformedUserException extends UserException{
	public MalformedUserException() {
		super("Invalid User input type!");
	}

	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.INVALID_INPUT_TYPE;
	}

	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
