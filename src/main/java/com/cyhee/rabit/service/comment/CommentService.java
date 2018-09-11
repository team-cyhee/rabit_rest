
package com.cyhee.rabit.service.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;

public interface CommentService {
	
	Page<Comment> getComments(ContentType type, Long parentid, Pageable pageable);
	
	Page<Comment> getComments(Pageable pageable);
	
	void addComment(Comment comment);
	
	Comment getComment(long id);
	
	void updateComment(long id, Comment source);
	
	void deleteComment(long id);
}
