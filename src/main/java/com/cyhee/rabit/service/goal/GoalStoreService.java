package com.cyhee.rabit.service.goal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.dao.like.LikeRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.like.LikeService;

@Service
public class GoalStoreService {
	@Autowired
	GoalService goalService;

	@Autowired
	GoalLogService goalLogService;
	
	@Autowired
	CommentStoreService commentStoreService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	LikeService likeService;

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	GoalLogStoreService goalLogStoreService;

	public void deleteGoal(long id) {
		Goal goal = goalService.deleteGoal(id);
		deleteAllGoalStore(goal);
	}

	public List<GoalLog> getGoalLogs(Goal goal) {
        return goalLogService.getGoalLogsByGoal(goal);
    }

	public Page<GoalLog> getGoalLogs(Goal goal, List<ContentStatus> statusList, Pageable pageable) {
		return goalLogService.getGoalLogsByGoalAndStatusIn(goal, statusList, pageable);
	}
	
	public Page<Comment> getComments(Goal goal, Pageable pageable) {
		return commentRepository.findByTypeAndParentIdAndStatusIn(ContentType.GOAL, goal.getId(), ContentStatus.visible(), pageable);
	}

	public List<Comment> getComments(Goal goal) {
		return commentRepository.findByTypeAndParentIdAndStatusIn(ContentType.GOAL, goal.getId(), ContentStatus.visible());
	}

	public Integer getCommentNum(Goal goal) {
		return commentRepository.findNumByParentIdAndStatusIn(ContentType.GOAL, goal.getId(), ContentStatus.visible());
	}

	public Page<Like> getLikes(Goal goal, Pageable pageable) {
		return likeService.getLikes(ContentType.GOAL, goal.getId(), pageable);
	}

	public List<Like> getLikes(Goal goal) {
		return likeService.getLikes(ContentType.GOAL, goal.getId());
	}
	
	public void addLike(Goal goal, User user) {
		// TODO specific exception
		if(goal.getAuthor().equals(user))
			throw new RuntimeException("Self like is not allowed");
		Like like = new Like().setAuthor(user).setType(ContentType.GOAL).setParentId(goal.getId());
		likeService.addLike(like);
	}

	public Integer getLikeNum(Goal goal) {
		return likeRepository.findNumByParentIdAndStatusIn(ContentType.GOAL, goal.getId(), RadioStatus.visible());
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
