package com.cyhee.rabit.user.web.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.cmm.web.model.ApiError;
import com.cyhee.rabit.user.exception.UserException;

@ControllerAdvice
@RestController
public class UserExceptionHandler {
	
	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<ApiError> userExceptionHandler(UserException e) {
		return new ResponseEntity<>(new ApiError(e.getApiErrorCode(), e), e.getStatus());
	}
}
