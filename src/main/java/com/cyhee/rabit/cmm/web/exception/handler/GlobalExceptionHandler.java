package com.cyhee.rabit.cmm.web.exception.handler;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.cmm.web.exception.NoSuchContentException;
import com.cyhee.rabit.cmm.web.model.ApiError;
import com.cyhee.rabit.cmm.web.model.ApiErrorCode;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = NoSuchContentException.class)
	public ResponseEntity<ApiError> notFoundExceptionHandler(NoSuchContentException e) {
		return new ResponseEntity<>(new ApiError(e.getApiErrorCode(), e), e.getStatus());
	}
	
	@ExceptionHandler(value = EmptyResultDataAccessException.class)
	public ResponseEntity<ApiError> emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException e) {
		return new ResponseEntity<>(new ApiError(ApiErrorCode.NOT_FOUND, e), HttpStatus.NOT_FOUND);
	}
}
