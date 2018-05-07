package com.cyhee.rabit.web.model;

public class ApiSuccess<T> extends ApiResult<T> {
	private T data;
	
	public ApiSuccess(T data) {
		this.data = data;
		this.success = true;
	}
	
	public ApiSuccess(T data, String message) {
		this.data = data;
		this.success = true;
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
