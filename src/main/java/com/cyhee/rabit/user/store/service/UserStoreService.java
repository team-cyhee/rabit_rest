package com.cyhee.rabit.user.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.goal.dao.GoalRepository;
import com.cyhee.rabit.goal.model.Goal;
import com.cyhee.rabit.goallog.dao.GoalLogRepository;
import com.cyhee.rabit.goallog.model.GoalLog;
import com.cyhee.rabit.user.model.User;

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
