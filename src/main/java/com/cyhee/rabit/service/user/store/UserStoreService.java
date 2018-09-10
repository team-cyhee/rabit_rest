package com.cyhee.rabit.service.user.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.goal.GoalRepository;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.dao.goallog.GoalLogRepository;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

@Service
public class UserStoreService {
	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private GoalLogRepository goalLogRepository;

	public Page<Goal> getGoals(User author, Pageable pageable) {
		return goalRepository.findAllByAuthor(author, pageable);
	}
	
	public Page<GoalLog> getGoalLogs(User author, Pageable pageable) {
		return goalLogRepository.findAllByAuthor(author, pageable);
	}

}
