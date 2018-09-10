package com.cyhee.rabit.model.cmm;

/**
 * 객체의 현재 상태를 나타내는 enum
 * @author chy
 *
 */
public enum ContentStatus {
	// 활성
	ACTIVE,
	// 비활성
	INACTIVE,
	// 금지됨
	FORBIDDEN,
	// 삭제됨
	DELETED,
	// 게시 보류중
	PENDING;
}
