package com.cyhee.rabit.model.cmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 팔로우의 현재 상태를 나타내는 enum
 * @author time-runner
 */

public enum RadioStatus {
    // 활성
    ACTIVE,
    // 비활성
    INACTIVE,
    // 금지됨
    FORBIDDEN;


    private static List<RadioStatus> list;
    private static List<RadioStatus> visibleList;

    public static List<RadioStatus> visible() {
        if (visibleList == null) {
            visibleList = new ArrayList<>();
            visibleList.add(ACTIVE);
        }
        return visibleList;
    }
    
    public static List<RadioStatus> all() {
    	if (list == null) {
    		list = new ArrayList<>(Arrays.asList(values())); 
    	}
    	return list;
    }
}
