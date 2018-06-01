package com.cyhee.rabit.goal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.goal.dao.GoalRepository;
import com.cyhee.rabit.goal.model.Goal;

@Service("basicGoalService")
public class BasicGoalService implements GoalService {
	@Autowired
	private GoalRepository goalRepository;

	public Iterable<Goal> getAllGoals() {
		return goalRepository.findAll();
	}

	public void addGoal(Goal goal) {
		goalRepository.save(goal);
	}

	public Goal getGoal(long id) {
		return goalRepository.findById(id).get();
	}

	public void updateGoal(long id, Goal goalForm) {
		Goal goal = goalRepository.findById(id).get();
		goal = goalForm;
		goalRepository.save(goal);
	}

	public void deleteGoal(long id) {
		goalRepository.deleteById(id);
	}
	
}
