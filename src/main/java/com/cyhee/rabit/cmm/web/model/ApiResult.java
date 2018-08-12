package com.cyhee.rabit.cmm.web.model;

public abstract class ApiResult<T> {
	protected String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
