package com.cyhee.rabit.service.cmm;

import com.cyhee.rabit.exception.cmm.UnsupportedContentException;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;

public class ContentHelper {
	
	/**
	 * author를 가지는 content에 대하여 owner를 가져온다
	 */
	public static User getOwner(Object content) {
		if(content instanceof Goal)
			return ((Goal)content).getAuthor();
		if(content instanceof GoalLog)
			return ((GoalLog)content).getGoal().getAuthor();
		if(content instanceof Comment)
			return ((Comment)content).getAuthor();
		if(content instanceof Follow)
			return ((Follow)content).getFollower();
		if(content instanceof Like)
			return ((Like)content).getAuthor();
		if(content instanceof User)
			return (User)content;
		
		throw new UnsupportedContentException();
	}
}
