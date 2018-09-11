package com.cyhee.rabit.service.goal;

import java.util.Optional;

import com.cyhee.rabit.model.cmm.ContentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.dao.goal.GoalRepository;
import com.cyhee.rabit.model.goal.Goal;

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
	}

	public void deleteGoal(long id) {
		Goal goal = getGoal(id);
		goal.setStatus(ContentStatus.DELETED);
	}
	
	private void setGoalProps(Goal target, Goal source) {
		target.setContent(source.getContent());
		target.setStartDate(source.getStartDate());
		target.setEndDate(source.getEndDate());
		target.setStatus(source.getStatus());
		target.setSelectedDays(source.getSelectedDays());
	}
}
