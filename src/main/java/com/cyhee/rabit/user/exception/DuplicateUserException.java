package com.cyhee.rabit.user.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.cmm.web.model.ApiErrorCode;

@SuppressWarnings("serial")
public class DuplicateUserException extends UserException{
	public DuplicateUserException() {
		super("User duplicated!");
	}

	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.DUPLICATED_KEY;
	}

	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
