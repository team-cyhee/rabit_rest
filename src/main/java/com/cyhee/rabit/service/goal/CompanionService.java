package com.cyhee.rabit.service.goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.goal.CompanionGoalRepository;
import com.cyhee.rabit.dao.goal.CompanionRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;

@Service
public class CompanionService {
	@Autowired
	CompanionRepository companionRepository;
	@Autowired
	CompanionGoalRepository companionGoalRepository;
	
	public Page<User> getCompanions(Goal goal, Pageable pageable) {
		Goal root = goal.getParent();
		if(root == null) root = goal;
		return companionRepository.findAllByGoal(root, goal, ContentStatus.visible(), pageable);
	}
	
	public Page<Goal> getCompanionGoals(Goal goal, Pageable pageable) {
		Goal root = goal.getParent();
		if(root == null) root = goal;
		return companionGoalRepository.findAllByGoal(root, goal, ContentStatus.visible(), pageable);
	}
}
