
package com.cyhee.rabit.goallog.service;

import com.cyhee.rabit.goallog.model.GoalLog;

public interface GoalLogService {
	Iterable<GoalLog> getAllGoalLogs();
	
	void addGoalLog(GoalLog user);
	
	GoalLog getGoalLog(long id);
	
	void updateGoalLog(long id, GoalLog goallogForm);
	
	void deleteGoalLog(long id);
}
