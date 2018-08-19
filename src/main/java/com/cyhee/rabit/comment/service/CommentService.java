
package com.cyhee.rabit.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.comment.model.Comment;

public interface CommentService {
	
	Page<Comment> getComments(ContentType type, Long parentid, Pageable pageable);
	
	Page<Comment> getComments(Pageable pageable);
	
	void addComment(Comment comment);
	
	Comment getComment(long id);
	
	void updateComment(long id, Comment source);
	
	void deleteComment(long id);
}
