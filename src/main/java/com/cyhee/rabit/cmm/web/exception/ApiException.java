package com.cyhee.rabit.cmm.web.exception;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.cmm.web.model.ApiErrorCode;

/**
 * Rest Api에서 발생하는 Exception에 대한 interface
 * 해당 interface를 구현한 Exception을 이용할 것을 권장
 * @author chy
 *
 */
public interface ApiException {
	ApiErrorCode getApiErrorCode();	
	HttpStatus getStatus();
	String getMessage();
}
