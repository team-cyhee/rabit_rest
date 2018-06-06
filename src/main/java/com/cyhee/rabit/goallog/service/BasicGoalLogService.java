
package com.cyhee.rabit.goallog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.goallog.dao.GoalLogRepository;
import com.cyhee.rabit.goallog.model.GoalLog;

@Service("basicGoalLogService")
public class BasicGoalLogService implements GoalLogService {
	@Autowired
	private GoalLogRepository goallogRepository;

	public Iterable<GoalLog> getAllGoalLogs() {
		return goallogRepository.findAll();
	}

	public void addGoalLog(GoalLog goallog) {
		goallogRepository.save(goallog);
	}

	public GoalLog getGoalLog(long id) {
		return goallogRepository.findById(id).get();
	}

	public void updateGoalLog(long id, GoalLog goallogForm) {
		GoalLog goallog = goallogRepository.findById(id).get();
		goallog = goallogForm;
		goallogRepository.save(goallog);
	}

	public void deleteGoalLog(long id) {
		goallogRepository.deleteById(id);
	}
	
}
