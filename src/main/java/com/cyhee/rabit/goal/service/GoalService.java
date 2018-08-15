package com.cyhee.rabit.goal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.goal.model.Goal;

public interface GoalService {
	Page<Goal> getGoals(Pageable pageable);
	
	void addGoal(Goal user);
	
	Goal getGoal(long id);
	
	void updateGoal(long id, Goal goalForm);
	
	void deleteGoal(long id);
}
