package com.cyhee.rabit.exception.cmm;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.exception.ApiException;
import com.cyhee.rabit.web.cmm.model.ApiErrorCode;

public class UnauthorizedException extends ApiException {

	private static final long serialVersionUID = 2701283839580016197L;
	
	public UnauthorizedException() {
	}
	
	public UnauthorizedException(String msg) {
		super(msg);
	}

	@Override
	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.UNAUTHORIZED;
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

}
