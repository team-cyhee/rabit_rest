package com.cyhee.rabit.service.goallog.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.model.goallog.GoalLog;

@Service
public class GoalLogStoreService {
	
	@Autowired
	CommentService commentService;
	
	public Page<Comment> getComments(GoalLog goalLog, Pageable pageable) {
		return commentService.getComments(ContentType.GOALLOG, goalLog.getId(), pageable);
	}
}
