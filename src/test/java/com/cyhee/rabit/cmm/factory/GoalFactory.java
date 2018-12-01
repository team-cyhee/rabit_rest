package com.cyhee.rabit.cmm.factory;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;

public class GoalFactory {
	public static Goal base(User author, String content) {
		Goal goal = new Goal();
		goal.setAuthor(author);
		goal.setContent(content);
		return goal;
	}
	
	public static Goal withParent(User author, String content, Goal parent) {
		Goal goal = new Goal();
		goal.setAuthor(author);
		goal.setContent(content);
		goal.setParent(parent);
		return goal;
	}
}
