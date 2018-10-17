package com.cyhee.rabit.service.cmm;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

public class ContentHelper {
	
	/**
	 * author를 가지는 content에 대하여 author를 가져온다
	 */
	public static User getAuthor(Object content) {
		if(content instanceof Goal)
			return ((Goal)content).getAuthor();
		if(content instanceof GoalLog)
			return ((GoalLog)content).getGoal().getAuthor();
		if(content instanceof Comment)
			return ((Comment)content).getAuthor();
		return null;
	}
}
