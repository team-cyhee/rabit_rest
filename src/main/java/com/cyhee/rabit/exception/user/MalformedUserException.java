package com.cyhee.rabit.exception.user;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.web.cmm.model.ApiErrorCode;

/**
 * user에 대한 입력이 잘못되었을 때 발생하는 exception
 * @author chy
 *
 */
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
