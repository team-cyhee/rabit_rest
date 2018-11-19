package com.cyhee.rabit.exception.file;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.exception.ApiException;
import com.cyhee.rabit.web.cmm.model.ApiErrorCode;

public class InvalidFileException extends ApiException {

	private static final long serialVersionUID = 1L;
	public InvalidFileException(String message) {
		super(message);
	}
	@Override
	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.INVALID_INPUT_TYPE;
	}
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
