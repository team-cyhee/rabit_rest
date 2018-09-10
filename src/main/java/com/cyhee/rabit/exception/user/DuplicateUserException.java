package com.cyhee.rabit.exception.user;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.web.cmm.model.ApiErrorCode;

/**
 * User�� unique key�� �ߺ��Ǿ��� �� �߻��ϴ� exception
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
