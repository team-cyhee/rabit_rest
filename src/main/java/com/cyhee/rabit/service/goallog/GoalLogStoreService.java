package com.cyhee.rabit.service.goallog;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.dao.like.LikeRepository;
import com.cyhee.rabit.exception.cmm.UnsupportedContentException;
import com.cyhee.rabit.model.cmm.BaseEntity;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.like.LikeService;

@Service
public class GoalLogStoreService {

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

	public void deleteGoalLog(long id) {
		GoalLog gl = goalLogService.getGoalLog(id);
		
		AuthHelper.isAuthorOrAdmin(gl);
		
		goalLogService.delete(gl);
		deleteAllGoalLogStore(gl);
	}
	
	public void deleteByParent(BaseEntity parent) {
		ContentType type = ContentType.findByKey(parent.getClass());		

		List<GoalLog> list = null;
		switch(type) {
			case GOAL:
				list = goalLogService.getGoalLogsByGoal((Goal)parent);
				break;
			case USER:
				list = goalLogService.getGoalLogsByAuthor((User)parent);
				break;
			default:
				new UnsupportedContentException("This paremeter should be GOAL or USER");
		}
		
		for(GoalLog gl : list) {
			goalLogService.delete(gl);
			deleteAllGoalLogStore(gl);
		}
	}

	public Page<Comment> getComments(GoalLog goalLog, Pageable pageable) {
		return commentRepository.findByTypeAndParentIdAndStatusIn(ContentType.GOALLOG, goalLog.getId(), ContentStatus.visible(), pageable);
	}

	public List<Comment> getComments(GoalLog goalLog) {
		return commentRepository.findByTypeAndParentIdAndStatusIn(ContentType.GOALLOG, goalLog.getId(), ContentStatus.visible());
	}

	public Integer getCommentNum(GoalLog goalLog) {
		return commentRepository.findNumByParentIdAndStatusIn(ContentType.GOALLOG, goalLog.getId(), ContentStatus.visible());
	}

	public Page<Like> getLikes(GoalLog goalLog, Pageable pageable) {
		return likeService.getLikes(ContentType.GOALLOG, goalLog.getId(), pageable);
	}

	public List<Like> getLikes(GoalLog goalLog) {
		return likeService.getLikes(ContentType.GOALLOG, goalLog.getId());
	}

	public Page<User> getLikers(GoalLog goalLog, Pageable pageable) {
		return likeService.getLikers(ContentType.GOALLOG, goalLog.getId(), pageable);
	}
	
	public void addLike(GoalLog goalLog, User liker) {
		// TODO specific exception
		/*if(goalLog.getGoal().getAuthor().equals(liker))
			throw new RuntimeException("Self like is not allowed");*/
		Like like = new Like().setAuthor(liker).setType(ContentType.GOALLOG).setParentId(goalLog.getId());
		likeService.addLike(like);
	}

	public Integer getLikeNum(GoalLog goalLog) {
		return likeRepository.findNumByParentIdAndStatusIn(ContentType.GOALLOG, goalLog.getId(), RadioStatus.visible());
	}

	private void deleteAllGoalLogStore(GoalLog goalLog) {
        commentStoreService.deleteByParent(goalLog);
        likeService.deleteByParent(goalLog);
	}
}
