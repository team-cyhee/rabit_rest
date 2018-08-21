package com.cyhee.rabit.user.model;

/**
 * User 모델이 {@link com.fasterxml.jackson.annotation#JsonView}를 활용할 때
 * 사용하는 view 객체
 * @author chy
 */
public class UserView {
	// 객체 생성 방지
	private UserView() {}
	
	public static class UserPost {}	
	public static class UserGet {}
}