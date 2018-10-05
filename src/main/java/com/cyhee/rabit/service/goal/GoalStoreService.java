package com.cyhee.rabit.service.goal;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;

import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.like.LikeService;
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
	CommentStoreService commentStoreService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	LikeService likeService;

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
		return commentRepository.findByTypeAndParentIdAndStatusNot(ContentType.GOAL, goal.getId(), ContentStatus.DELETED, pageable);
	}

	public List<Comment> getComments(Goal goal) {
		return commentRepository.findByTypeAndParentIdAndStatusNot(ContentType.GOAL, goal.getId(), ContentStatus.DELETED);
	}

	public Page<Like> getLikes(Goal goal, Pageable pageable) {
		return likeService.getLikes(ContentType.GOAL, goal.getId(), pageable);
	}

	public List<Like> getLikes(Goal goal) {
		return likeService.getLikes(ContentType.GOAL, goal.getId());
	}

	public void deleteAllGoalStore(Goal goal) {
		for (GoalLog gl : getGoalLogs(goal)) {
			goalLogStoreService.deleteGoalLog(gl.getId());
		}
		for (Comment cmt : getComments(goal)) {
			commentStoreService.deleteComment(cmt.getId());
		}
		for (Like like : getLikes(goal)) {
			likeService.deleteLike(like.getId());
		}
	}

}
