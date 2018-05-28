package com.cyhee.rabit.goal.service;

import com.cyhee.rabit.goal.model.Goal;

public interface GoalService {
	Iterable<Goal> getAllGoals();
	
	void addGoal(Goal user);
	
	Goal getGoal(int id);
	
	void updateGoal(int id, Goal goalForm);
	
	void deleteGoal(int id);
}
