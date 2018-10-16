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
	private static List<ContentStatus> visibleList;
	
	
	public static List<ContentStatus> all() {
		if(list == null) {
			list = new ArrayList<>(Arrays.asList(ContentStatus.values()));
		}
		return list;
	}
	
	public static List<ContentStatus> visible() {
		if(visibleList == null) {
			visibleList = new ArrayList<>();
			visibleList.add(ACTIVE);
		}
		return visibleList;
	}
}
