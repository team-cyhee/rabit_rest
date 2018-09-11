package com.cyhee.rabit.service.goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.dao.goallog.GoalLogRepository;
import com.cyhee.rabit.model.goallog.GoalLog;

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
