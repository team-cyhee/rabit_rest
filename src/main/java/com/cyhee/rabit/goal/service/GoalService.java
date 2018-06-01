package com.cyhee.rabit.goal.service;

import com.cyhee.rabit.goal.model.Goal;

public interface GoalService {
	Iterable<Goal> getAllGoals();
	
	void addGoal(Goal user);
	
	Goal getGoal(long id);
	
	void updateGoal(long id, Goal goalForm);
	
	void deleteGoal(long id);
}
