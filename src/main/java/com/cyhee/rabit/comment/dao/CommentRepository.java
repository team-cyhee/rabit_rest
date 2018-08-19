
package com.cyhee.rabit.comment.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.comment.model.Comment;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
	Page<Comment> findByTypeAndParentId(ContentType type, Long parentId, Pageable pageable);
}
