
package com.cyhee.rabit.dao.comment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
	Page<Comment> findByTypeAndParentIdAndStatusIn(ContentType type, Long parentId, List<ContentStatus> status, Pageable pageable);

	List<Comment> findByTypeAndParentIdAndStatusIn(ContentType type, Long parentId, List<ContentStatus> status);

	Page<Comment> findByStatusIn(List<ContentStatus> status, Pageable pageable);

	@Query("Select count(n) From Comment n Where :cType = n.type AND n.parentId = :parentId AND n.status In :statusList")
	Integer findNumByParentIdAndStatusIn(@Param("cType") ContentType cType, @Param("parentId") Long parentId, @Param("statusList") List<ContentStatus> statusList);
}
