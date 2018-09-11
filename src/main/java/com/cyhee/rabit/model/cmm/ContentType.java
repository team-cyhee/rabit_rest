package com.cyhee.rabit.model.cmm;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

/**
 * 해당 프로젝트에서 생성되어 사용되는 모든 entity 객체에 대한 type
 * @author chy
 *
 */
public enum ContentType {
	USER(User.class), FOLLOW(Follow.class), GOAL(Goal.class), GOALLOG(GoalLog.class), COMMENT(Comment.class);
	
	private Class<?> clazz;
	
	ContentType(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public Class<?> getClazz() {
		return this.clazz;
	}

}
