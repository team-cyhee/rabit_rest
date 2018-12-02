package com.cyhee.rabit.service.cmm;


import org.springframework.data.util.Pair;

import com.cyhee.rabit.exception.cmm.UnsupportedContentException;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.cs.Question;
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
		if(content instanceof Question)
			return ((Question)content).getAuthor();
		
		throw new UnsupportedContentException();
	}
	
	/**
	 * parent로 goal 혹은 goalLog를 가지는 content에 대하여 parent의 id와 type을 return
	 */
	public static Pair<Long, ContentType> getParentPair(Object content) {
		if(content instanceof Goal) {
			Goal parent = ((Goal)content).getParent();
			if(parent == null)
				return null;
			return Pair.of(parent.getId(), ContentType.GOAL);
		}
		if(content instanceof GoalLog) {
			Goal parent = ((GoalLog)content).getGoal();
			return Pair.of(parent.getId(), ContentType.GOAL);
		}
		if(content instanceof Comment) {
			Comment self = (Comment)content;
			return Pair.of(self.getParentId(), self.getType());
		}
		if(content instanceof Like) {
			Like self = (Like)content;
			return Pair.of(self.getParentId(), self.getType());
		}
		return null;
//		throw new UnsupportedContentException();
	}
}
