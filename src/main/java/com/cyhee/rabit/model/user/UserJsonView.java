package com.cyhee.rabit.model.user;

/**
 * User 모델이 {@link com.fasterxml.jackson.annotation#JsonView}를 활용할 때
 * 사용하는 view 객체, 해당 또는 하위 클래스의 instance를 생성하지 않도록 할 것.
 * @author chy
 */
public class UserJsonView {
	// 객체 생성 방지
	private UserJsonView() {}
	
	public static class UserPost {}	
	public static class UserGet {}
}