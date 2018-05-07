package com.cyhee.rabit.web.model;

public class ApiError<T> extends ApiResult<T>{
	private ApiErrorCode error;
	
	public ApiError() {
		this.error = ApiErrorCode.UNKNOWN;
		this.success = false;
	}
	
	public ApiError(ApiErrorCode code, Throwable e) {
		this.error = code;
		this.message = e.getMessage();
		this.success = false;
	}

	public ApiError(ApiErrorCode code, String message) {
		this.error = code;
		this.message = message;
		this.success = false;
	}

	public ApiErrorCode getError() {
		return error;
	}

	public void setError(ApiErrorCode error) {
		this.error = error;
	}
}
