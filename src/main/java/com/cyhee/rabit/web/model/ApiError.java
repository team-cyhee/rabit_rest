package com.cyhee.rabit.web.model;

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

	public ApiErrorCode getError() {
		return error;
	}

	public void setError(ApiErrorCode error) {
		this.error = error;
	}
}
