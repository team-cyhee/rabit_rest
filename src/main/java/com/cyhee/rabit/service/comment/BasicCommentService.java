
package com.cyhee.rabit.service.comment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.web.cmm.exception.NoSuchContentException;
import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.model.comment.Comment;

@Service("basicCommentService")
public class BasicCommentService implements CommentService {
	@Autowired
	private CommentRepository repository;
	
	public Page<Comment> getComments(ContentType type, Long parentid, Pageable pageable) {
		return repository.findByTypeAndParentId(type, parentid, pageable);
	}

	public Page<Comment> getComments(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public void addComment(Comment comment) {
		repository.save(comment);
	}

	public Comment getComment(long id) {
		Optional<Comment> comment = repository.findById(id);
		if(!comment.isPresent())
			throw new NoSuchContentException(ContentType.COMMENT, id);
		return comment.get();
	}

	public void updateComment(long id, Comment source) {
		Comment comment = getComment(id);
		setCommentProps(comment, source);
		repository.save(comment);
	}

	public void deleteComment(long id) {
		repository.deleteById(id);
	}
	
	private void setCommentProps(Comment target, Comment source) {
		target.setContent(source.getContent());
	}
}
