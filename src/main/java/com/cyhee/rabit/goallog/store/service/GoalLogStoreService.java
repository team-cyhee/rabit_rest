package com.cyhee.rabit.goallog.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.comment.model.Comment;
import com.cyhee.rabit.comment.service.CommentService;
import com.cyhee.rabit.goallog.model.GoalLog;

@Service
public class GoalLogStoreService {
	
	@Autowired
	CommentService commentService;
	
	public Page<Comment> getComments(GoalLog goalLog, Pageable pageable) {
		return commentService.getComments(ContentType.GOALLOG, goalLog.getId(), pageable);
	}
}
