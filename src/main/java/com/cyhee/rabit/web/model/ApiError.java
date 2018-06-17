package com.cyhee.rabit.web.model;

import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ApiError<T> extends ApiResult<T>{
	private ApiErrorCode error;
	
	public ApiError() {
		this.error = ApiErrorCode.UNKNOWN;
	}
	
	public ApiError(ApiErrorCode code, Throwable e) {
		this.error = code;
		this.message = e.getMessage();
	}

	public ApiError(ApiErrorCode code, String message) {
		this.error = code;
		this.message = message;
	}
	
	public ApiError(List<ObjectError> errors) {
		this.error = ApiErrorCode.INVALID_INPUT_TYPE;
		this.message = "";
		
		errors.forEach(error -> {
			if(error instanceof FieldError) {
				FieldError fieldError = (FieldError)error;
				this.message += fieldError.getField() + " : " + fieldError.getDefaultMessage() + "\n";
			}
		});
	}

	public ApiErrorCode getError() {
		return error;
	}

	public void setError(ApiErrorCode error) {
		this.error = error;
	}
}
