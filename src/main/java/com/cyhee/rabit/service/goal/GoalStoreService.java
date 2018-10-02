package com.cyhee.rabit.service.goal;

import com.cyhee.rabit.model.cmm.ContentStatus;

import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.dao.goallog.GoalLogRepository;
import com.cyhee.rabit.model.goallog.GoalLog;

import java.util.List;

@Service
public class GoalStoreService {
	@Autowired
	GoalService goalService;

	@Autowired
	GoalLogRepository goalLogRepository;
	
	@Autowired
	CommentService commentService;

	@Autowired
	GoalLogStoreService goalLogStoreService;

	public void deleteGoal(long id) {
		Goal goal = goalService.deleteGoal(id);
		deleteAllGoalStore(goal);
	}

	public Page<GoalLog> getGoalLogs(Goal goal, Pageable pageable) {
		return goalLogRepository.findByGoalAndStatusNot(goal, ContentStatus.DELETED, pageable);
	}

	public List<GoalLog> getGoalLogs(Goal goal) {
        return goalLogRepository.findByGoalAndStatusNot(goal, ContentStatus.DELETED);
    }
	
	public Page<Comment> getComments(Goal goal, Pageable pageable) {
		return commentService.getComments(ContentType.GOAL, goal.getId(), pageable);
	}

	public List<Comment> getComments(Goal goal) {
		return commentService.getComments(ContentType.GOAL, goal.getId());
	}

	public void deleteAllGoalStore(Goal goal) {
		for (GoalLog gl : getGoalLogs(goal)) {
			goalLogStoreService.deleteGoalLog(gl.getId());
		}
		for (Comment cmt : getComments(goal)) {
			commentService.deleteComment(cmt.getId());
		}
	}
}
