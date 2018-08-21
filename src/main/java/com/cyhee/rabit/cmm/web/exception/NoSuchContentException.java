package com.cyhee.rabit.cmm.web.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.cmm.web.model.ApiErrorCode;

/**
 * 접근을 시도한 객체가 존재하지 않을 때 발생시키는 Exception
 * @author chy
 *
 */
public class NoSuchContentException extends RuntimeException implements ApiException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchContentException(ContentType type) {
		super(String.format("No such %s.", type.toString().toLowerCase()));
	}
	
	public NoSuchContentException(ContentType type, Long id) {
		super(String.format("No such %s with id '%lld'.", type.toString().toLowerCase(), id));
	}
	
	public NoSuchContentException(ContentType type, Integer id) {
		super(String.format("No such %s with id '%d'.", type.toString().toLowerCase(), id));
	}
	
	public NoSuchContentException(ContentType type, String key, Object value) {
		super(String.format("No such %s with %s '%s'.", type.toString().toLowerCase(), key, value.toString()));
	}

	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.NOT_FOUND;
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
