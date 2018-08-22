package com.cyhee.rabit.goal.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.comment.model.Comment;
import com.cyhee.rabit.comment.service.CommentService;
import com.cyhee.rabit.goal.model.Goal;
import com.cyhee.rabit.goallog.dao.GoalLogRepository;
import com.cyhee.rabit.goallog.model.GoalLog;

@Service
public class GoalStoreService {
	
	@Autowired
	GoalLogRepository goalLogRepository;
	
	@Autowired
	CommentService commentService;
	
	public Page<GoalLog> getGoalLogs(Goal goal, Pageable pageable) {
		return goalLogRepository.findAllByGoal(goal, pageable);
	}
	
	public Page<Comment> getComments(Goal goal, Pageable pageable) {
		return commentService.getComments(ContentType.GOAL, goal.getId(), pageable);
	}
}
