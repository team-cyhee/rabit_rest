package com.cyhee.rabit.goal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.cmm.web.exception.NoSuchContentException;
import com.cyhee.rabit.goal.dao.GoalRepository;
import com.cyhee.rabit.goal.model.Goal;

@Service("basicGoalService")
public class BasicGoalService implements GoalService {
	@Autowired
	private GoalRepository goalRepository;

	public Page<Goal> getGoals(Pageable pageable) {
		return goalRepository.findAll(pageable);
	}

	public void addGoal(Goal goal) {
		goalRepository.save(goal);
	}

	public Goal getGoal(long id) {
		Optional<Goal> goal = goalRepository.findById(id);
		if(!goal.isPresent())
			throw new NoSuchContentException(ContentType.GOAL, id);
		return goal.get();
	}

	public void updateGoal(long id, Goal goalForm) {
		Goal goal = getGoal(id);
		setGoalProps(goal, goalForm);
		goalRepository.save(goal);
	}

	public void deleteGoal(long id) {
		goalRepository.deleteById(id);
	}
	
	private void setGoalProps(Goal target, Goal source) {
		target.setContent(source.getContent());
		target.setStartDate(source.getStartDate());
		target.setEndDate(source.getEndDate());
		target.setStatus(source.getStatus());
		target.setSelectedDays(source.getSelectedDays());
	}
}
