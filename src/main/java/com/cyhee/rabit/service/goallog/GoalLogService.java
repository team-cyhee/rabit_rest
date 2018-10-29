
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
import com.cyhee.rabit.service.cmm.AuthHelper;

@Service("goalLogService")
public class GoalLogService {

	@Autowired
	private GoalLogRepository goalLogRepository;

	public Page<GoalLog> getGoalLogs(Pageable pageable) {
		return goalLogRepository.findAll(pageable);
	}

	public Page<GoalLog> getGoalLogsByStatusIn(List<ContentStatus> statusList, Pageable pageable) {
		return goalLogRepository.findAllByStatusIn(statusList, pageable);
	}

	public GoalLog getGoalLog(long id) {
		Optional<GoalLog> log = goalLogRepository.findById(id);
		if(!log.isPresent())
			throw new NoSuchContentException(ContentType.GOALLOG, id);
		return log.get();
	}
	
	public List<GoalLog> getGoalLogsByAuthor(User author) {
		return goalLogRepository.findAllByAuthor(author);
	}
		
	public Page<GoalLog> getGoalLogsByAuthorAndStatusIn(User author, List<ContentStatus> statusList, Pageable pageable) {
		return goalLogRepository.findAllByAuthorAndStatusIn(author, statusList, pageable);
	}

	public List<GoalLog> getGoalLogsByGoal(Goal goal) {
		return goalLogRepository.findAllByGoal(goal);
	}	

	public Page<GoalLog> getGoalLogsByGoalAndStatusIn(Goal goal, List<ContentStatus> statusList, Pageable pageable) {
		return goalLogRepository.findAllByGoalAndStatusIn(goal, statusList, pageable);
	}

	public void addGoalLog(GoalLog goalLog) {		
		AuthHelper.isAuthorOrAdmin(goalLog);
		goalLogRepository.save(goalLog);
	}
	public Integer getLogNumByGoalAndStatusIn(Goal goal, List<ContentStatus> statusList) {
		return goalLogRepository.findNumByGoalAndStatusIn(goal, statusList);
	}

	public void updateGoalLog(long id, GoalLog goalLogForm) {
		GoalLog log = getGoalLog(id);
		
		AuthHelper.isAuthorOrAdmin(log);
		
		setGoalLogProps(log, goalLogForm);
		goalLogRepository.save(log);
	}

	public GoalLog deleteGoalLog(long id) {
		GoalLog log = getGoalLog(id);
		
		AuthHelper.isAuthorOrAdmin(log);
		
		delete(log);
		return log;
	}
	
	void delete(GoalLog goalLog) {		
		goalLog.setStatus(ContentStatus.DELETED);
		goalLogRepository.save(goalLog);		
	}
	
	private void setGoalLogProps(GoalLog target, GoalLog source) {
		target.setContent(source.getContent());
	}
}
