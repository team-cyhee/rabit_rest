package com.cyhee.rabit.exception.user;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.web.cmm.model.ApiErrorCode;

/**
 * User의 unique key가 중복되었을 때 발생하는 exception
 * @author chy
 *
 */
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
