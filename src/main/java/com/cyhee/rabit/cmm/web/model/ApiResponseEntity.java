package com.cyhee.rabit.cmm.web.model;

@Deprecated
public class ApiResponseEntity<T> {/*extends ResponseEntity<ApiResult<T>>{

	public ApiResponseEntity(HttpStatus status) {
		super(new ApiSuccess<>(null), status);
		// it should be called when only success
		assert(status.is2xxSuccessful());
	}
		
	public ApiResponseEntity(@Nullable T body, HttpStatus status) {
		super(new ApiSuccess<>(body), status);
	}
	
	public ApiResponseEntity(@Nullable T body, String message, HttpStatus status) {
		super(new ApiSuccess<>(body, message), status);
	}
	
	public ApiResponseEntity(ApiErrorCode code, Throwable e, HttpStatus status) {
		super(new ApiError(code, e), status);
	}
	
	public ApiResponseEntity(ApiErrorCode code, String message, HttpStatus status) {
		super(new ApiError<>(code, message), status);
	}
	
	public ApiResponseEntity(List<ObjectError> errors) {
		super(new ApiError<>(errors), HttpStatus.BAD_REQUEST);
	}

	public ApiResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
		super(headers, status);
	}

	public ApiResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
		super(new ApiSuccess<T>(body), headers, status);
	}*/
}
