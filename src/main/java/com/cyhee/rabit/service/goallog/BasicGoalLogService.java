
package com.cyhee.rabit.service.goallog;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.web.cmm.exception.NoSuchContentException;
import com.cyhee.rabit.dao.goallog.GoalLogRepository;
import com.cyhee.rabit.model.goallog.GoalLog;

@Service("basicGoalLogService")
public class BasicGoalLogService implements GoalLogService {
	@Autowired
	private GoalLogRepository goallogRepository;

	public Page<GoalLog> getGoalLogs(Pageable pageable) {
		return goallogRepository.findAll(pageable);
	}

	public void addGoalLog(GoalLog goallog) {
		goallogRepository.save(goallog);
	}

	public GoalLog getGoalLog(long id) {
		Optional<GoalLog> log = goallogRepository.findById(id);
		if(!log.isPresent())
			throw new NoSuchContentException(ContentType.GOALLOG, id);
		return log.get();
	}

	public void updateGoalLog(long id, GoalLog goallogForm) {
		GoalLog log = getGoalLog(id);
		setGoalLogProps(log, goallogForm);
		goallogRepository.save(log);
	}

	public void deleteGoalLog(long id) {
		goallogRepository.deleteById(id);
	}
	
	private void setGoalLogProps(GoalLog target, GoalLog source) {
		target.setContent(source.getContent());
	}
}
