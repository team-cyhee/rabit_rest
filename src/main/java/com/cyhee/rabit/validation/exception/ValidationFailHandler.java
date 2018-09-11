package com.cyhee.rabit.validation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.web.cmm.model.ApiError;

/**
 * @{link ValidationFailException}�� ���� handler
 * @author chy
 *
 */
@ControllerAdvice
@RestController
public class ValidationFailHandler {
	
	@ExceptionHandler(value=ValidationFailException.class)
	public ResponseEntity<ApiError> validationFailHandler(ValidationFailException e) {
		return new ResponseEntity<>(new ApiError(e), e.getStatus());
	}
}
