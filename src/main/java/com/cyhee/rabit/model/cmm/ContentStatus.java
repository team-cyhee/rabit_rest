package com.cyhee.rabit.model.cmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	private static List<ContentStatus> list;
	
	public static List<ContentStatus> all() {
		if(ContentStatus.list == null) {
			ContentStatus.list = new ArrayList<ContentStatus>(Arrays.asList(ContentStatus.values()));
		}
		return list;
	}
}
