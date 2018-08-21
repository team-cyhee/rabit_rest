package com.cyhee.rabit.user.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * sns token을 이용하여 회원가입시 사용하는 packet 모델
 * 인증에 필요한 token과 회원가입 정보들을 인자로 가짐
 * @author chy
 */
@Data
public class SnsUser {
	
	private String token;
	
	@NotNull
	private String username;
	
	private String name;

	private String phone;
	 
	private Date birth;
}
