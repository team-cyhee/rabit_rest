package com.cyhee.rabit.service.goal;

import java.util.List;
import java.util.Optional;

import com.cyhee.rabit.aop.alarm.AddAlarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.goal.GoalRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;

@Service("goalService")
public class GoalService {
	@Autowired
	private GoalRepository goalRepository;

	public Page<Goal> getGoals(Pageable pageable) {
		return goalRepository.findAll(pageable);
	}

	public Page<Goal> getGoalsByStatusIn(List<ContentStatus> statusList, Pageable pageable) {
		return goalRepository.findAllByStatusIn(statusList, pageable);
	}

	public List<Goal> getGoalsByAuthor(User author) {
		return goalRepository.findAllByAuthor(author);
	}
	
	public Page<Goal> getGoalsByAuthorStatusIn(User author, List<ContentStatus> statusList, Pageable pageable) {
		return goalRepository.findAllByAuthorAndStatusIn(author, statusList, pageable);
	}

	@AddAlarm
	public Goal addGoal(User author, User owner, Goal goal) {
		return goalRepository.save(goal);
	}

	public Goal getGoal(long id) {
		Optional<Goal> goal = goalRepository.findById(id);
		if(!goal.isPresent())
			throw new NoSuchContentException(ContentType.GOAL, id);
		return goal.get();
	}
	
	public Page<Goal> search(String keyword, List<ContentStatus> statusList, Pageable pageable) {
		return goalRepository.search(keyword, statusList, pageable);
	}

	public void updateGoal(long id, Goal goalForm) {
		Goal goal = getGoal(id);
		
		AuthHelper.isAuthorOrAdmin(goal);
		
		setGoalProps(goal, goalForm);
		goalRepository.save(goal);
	}

	public Goal deleteGoal(long id) {
		Goal goal = getGoal(id);
		
		AuthHelper.isAuthorOrAdmin(goal);
		
		delete(goal);
		return goal;
	}
	
	void delete(Goal goal) {
		goal.setStatus(ContentStatus.DELETED);
		goalRepository.save(goal);		
	}
	
	private void setGoalProps(Goal target, Goal source) {
		target.setContent(source.getContent());
		target.setStartDate(source.getStartDate());
		target.setEndDate(source.getEndDate());
		target.setStatus(source.getStatus());
		target.setDoUnit(source.getDoUnit());
		target.setDoTimes(source.getDoTimes());
	}
}
