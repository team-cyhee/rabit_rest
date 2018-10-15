package com.cyhee.rabit.service.goallog;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.dao.like.LikeRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.model.goallog.GoalLog;

import java.util.Arrays;
import java.util.List;

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
		GoalLog gl = goalLogService.deleteGoalLog(id);
		deleteAllGoalLogStore(gl);
	}

	public Page<Comment> getComments(GoalLog goalLog, Pageable pageable) {
		return commentRepository.findByTypeAndParentIdAndStatusNot(ContentType.GOALLOG, goalLog.getId(), ContentStatus.DELETED, pageable);
	}

	public List<Comment> getComments(GoalLog goalLog) {
		return commentRepository.findByTypeAndParentIdAndStatusNot(ContentType.GOALLOG, goalLog.getId(), ContentStatus.DELETED);
	}

	public Integer getCommentNum(GoalLog goalLog) {
		return commentRepository.findNumByParentAndStatusIn(ContentType.GOALLOG, goalLog.getId(), Arrays.asList(ContentStatus.ACTIVE));
	}

	public Page<Like> getLikes(GoalLog goalLog, Pageable pageable) {
		return likeService.getLikes(ContentType.GOALLOG, goalLog.getId(), pageable);
	}

	public List<Like> getLikes(GoalLog goalLog) {
		return likeService.getLikes(ContentType.GOALLOG, goalLog.getId());
	}

	public Integer getLikeNum(GoalLog goalLog) {
		return likeRepository.findNumByParentAndStatusIn(ContentType.GOALLOG, goalLog.getId(), Arrays.asList(ContentStatus.ACTIVE));
	}

	public void deleteAllGoalLogStore(GoalLog goalLog) {
        for (Comment cmt : getComments(goalLog)) {
            commentStoreService.deleteComment(cmt.getId());
        }
		for (Like like : getLikes(goalLog)) {
			likeService.deleteLike(like.getId());
		}
	}
}
