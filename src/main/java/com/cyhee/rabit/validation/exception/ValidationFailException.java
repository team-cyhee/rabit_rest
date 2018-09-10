package com.cyhee.rabit.validation.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.cyhee.rabit.web.cmm.exception.ApiException;
import com.cyhee.rabit.web.cmm.model.ApiErrorCode;

import lombok.Getter;

/**
 * Validation test�� �������� ������ �� �߻��ϴ� exception
 * ���� ������ validation errors�� ������ ����
 * @author chy
 *
 */
@Getter
public class ValidationFailException extends RuntimeException implements ApiException {
	
	private static final long serialVersionUID = -6740715409616958417L;
	
	private List<ObjectError> errors;

	public ValidationFailException(List<ObjectError> errors) {
		super(getMessageFromErros(errors));
		this.errors = errors;
	}
	
	/**
	 * validation���� �߻��� errors�� �ϳ��� String message�� ������ִ� �Լ�
	 * @param errors - validation errors
	 * @return - one single String message from errors
	 */
	public static String getMessageFromErros(List<ObjectError> errors) {
		StringBuffer msg = new StringBuffer();
		errors.forEach(error -> {
			if(error instanceof FieldError) {
				FieldError fieldError = (FieldError)error;
				msg.append(fieldError.getField() + " : " + fieldError.getDefaultMessage() + ", ");
			}
		});
		// remove last ','
		return msg.delete(msg.length()-2, msg.length()-1).toString();
	}

	@Override
	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.INVALID_INPUT_TYPE;
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
