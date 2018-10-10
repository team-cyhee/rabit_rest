
package com.cyhee.rabit.service.goallog;

import java.util.Optional;

import com.cyhee.rabit.model.cmm.ContentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.dao.goallog.GoalLogRepository;
import com.cyhee.rabit.model.goallog.GoalLog;

@Service("goalLogService")
public class GoalLogService {

	@Autowired
	private GoalLogRepository goallogRepository;

	public Page<GoalLog> getGoalLogs(Pageable pageable) {
		return goallogRepository.findByStatusNot(ContentStatus.DELETED, pageable);
	}

	public GoalLog getGoalLog(long id) {
		Optional<GoalLog> log = goallogRepository.findById(id);
		if(!log.isPresent())
			throw new NoSuchContentException(ContentType.GOALLOG, id);
		return log.get();
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
