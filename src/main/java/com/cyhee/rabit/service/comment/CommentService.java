
package com.cyhee.rabit.service.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;

@Service("commentService")
public class CommentService {
	@Autowired
	private CommentRepository repository;
	
	public Page<Comment> getComments(ContentType type, Long parentId, Pageable pageable) {
		return repository.findByTypeAndParentIdAndStatusNot(type, parentId, ContentStatus.DELETED, pageable);
	}

	public List<Comment> getComments(ContentType type, Long parentId) {
		return repository.findByTypeAndParentIdAndStatusNot(type, parentId, ContentStatus.DELETED);
	}

	public Page<Comment> getComments(Pageable pageable) {
		return repository.findByStatusNot(ContentStatus.DELETED, pageable);
	}

	public Comment getComment(long id) {
		Optional<Comment> comment = repository.findById(id);
		if(!comment.isPresent())
			throw new NoSuchContentException(ContentType.COMMENT, id);
		return comment.get();
	}

	public void addComment(Comment comment) {
		repository.save(comment);
	}

	public void updateComment(long id, Comment source) {
		Comment comment = getComment(id);
		setCommentProps(comment, source);
		repository.save(comment);
	}

	public Comment deleteComment(long id) {
		Comment comment = getComment(id);
		comment.setStatus(ContentStatus.DELETED);
		repository.save(comment);
		return comment;
	}
	
	private void setCommentProps(Comment target, Comment source) {
		target.setContent(source.getContent());
	}
}
