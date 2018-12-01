package com.cyhee.rabit.cmm.factory;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;

public class GoalLogFactory {
	public static GoalLog base(Goal goal, String content) {
		GoalLog gl = new GoalLog();
		gl.setGoal(goal);
		gl.setContent(content);
		return gl;
	}
}
