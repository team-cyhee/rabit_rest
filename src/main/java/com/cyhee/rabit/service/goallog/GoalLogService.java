
package com.cyhee.rabit.service.goallog;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.goallog.GoalLogRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

@Service("goalLogService")
public class GoalLogService {

	@Autowired
	private GoalLogRepository goallogRepository;

	public Page<GoalLog> getGoalLogs(Pageable pageable) {
		return goallogRepository.findAll(pageable);
	}

	public GoalLog getGoalLog(long id) {
		Optional<GoalLog> log = goallogRepository.findById(id);
		if(!log.isPresent())
			throw new NoSuchContentException(ContentType.GOALLOG, id);
		return log.get();
	}
	
	public List<GoalLog> getGoalLogsByAuthor(User author) {
		return goallogRepository.findAllByAuthor(author);
	}
		
	public Page<GoalLog> getGoalLogsByAuthorAndStatusIn(User author, List<ContentStatus> statusList, Pageable pageable) {
		return goallogRepository.findAllByAuthorAndStatusIn(author, statusList, pageable);
	}

	public List<GoalLog> getGoalLogsByGoal(Goal goal) {
		return goallogRepository.findAllByGoal(goal);
	}	

	public Page<GoalLog> getGoalLogsByGoalAndStatusIn(Goal goal, List<ContentStatus> statusList, Pageable pageable) {
		return goallogRepository.findAllByGoalAndStatusIn(goal, statusList, pageable);
	}

	public void addGoalLog(GoalLog goallog) {
		goallogRepository.save(goallog);
	}

	public void updateGoalLog(long id, GoalLog goallogForm) {
		GoalLog log = getGoalLog(id);
		setGoalLogProps(log, goallogForm);
		goallogRepository.save(log);
	}

	public GoalLog deleteGoalLog(long id) {
		GoalLog log = getGoalLog(id);
		log.setStatus(ContentStatus.DELETED);
		goallogRepository.save(log);
		return log;
	}
	
	private void setGoalLogProps(GoalLog target, GoalLog source) {
		target.setContent(source.getContent());
	}
}
