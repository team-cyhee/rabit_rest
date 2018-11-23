package com.cyhee.rabit.model.cmm;

import java.util.HashMap;
import java.util.Map;

import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.cs.Question;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.notice.Notice;
import com.cyhee.rabit.model.user.User;

/**
 * 해당 프로젝트에서 생성되어 사용되는 모든 entity 객체에 대한 type
 * @author chy
 *
 */
public enum ContentType {
	USER(User.class), FOLLOW(Follow.class), GOAL(Goal.class),
	GOALLOG(GoalLog.class), COMMENT(Comment.class), FILE(FileInfo.class),
	LIKE(Like.class), NOTICE(Notice.class), QUESTION(Question.class),
	ALARM(Alarm.class);
	
	private Class<?> clazz;
	
	private static final Map<Class<?>,ContentType> map;
    static {
        map = new HashMap<>();
        for (ContentType v : ContentType.values())
            map.put(v.clazz, v);
    }
	
	ContentType(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public Class<?> getClazz() {
		return this.clazz;
	}
    
    public static ContentType findByKey(Class<?> clazz) {
        return map.get(clazz);
    }

}
