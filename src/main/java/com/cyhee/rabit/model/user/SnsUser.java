package com.cyhee.rabit.model.user;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * sns token�� �̿��Ͽ� ȸ�����Խ� ����ϴ� packet ��
 * ������ �ʿ��� token�� ȸ������ �������� ���ڷ� ����
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
