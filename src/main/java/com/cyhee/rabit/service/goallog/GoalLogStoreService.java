package com.cyhee.rabit.service.goallog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.model.goallog.GoalLog;

import java.util.List;

@Service
public class GoalLogStoreService {

	@Autowired
	GoalLogService goalLogService;

	@Autowired
	CommentService commentService;

	public void deleteGoalLog(long id) {
		GoalLog gl = goalLogService.deleteGoalLog(id);
		deleteAllGoalLogStore(gl);
	}

	public Page<Comment> getComments(GoalLog goalLog, Pageable pageable) {
		return commentService.getComments(ContentType.GOALLOG, goalLog.getId(), pageable);
	}

	public List<Comment> getComments(GoalLog goalLog) {
		return commentService.getComments(ContentType.GOALLOG, goalLog.getId());
	}

	public void deleteAllGoalLogStore(GoalLog goalLog) {
		getComments(goalLog).forEach(cmt -> commentService.deleteComment(cmt.getId()));
	}
}
