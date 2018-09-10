package com.cyhee.rabit.service.goal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.model.goal.Goal;

public interface GoalService {
	Page<Goal> getGoals(Pageable pageable);
	
	void addGoal(Goal goal);
	
	Goal getGoal(long id);
	
	void updateGoal(long id, Goal goalForm);
	
	void deleteGoal(long id);
}
