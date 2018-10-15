
package com.cyhee.rabit.dao.like;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.like.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface LikeRepository extends PagingAndSortingRepository<Like, Long> {
	Page<Like> findByTypeAndParentIdAndStatusNot(ContentType type, Long parentId, RadioStatus notStatus, Pageable pageable);

	List<Like> findByTypeAndParentIdAndStatusNot(ContentType type, Long parentId, RadioStatus notStatus);

	Page<Like> findByStatusNot(RadioStatus notStatus, Pageable pageable);

	@Query("Select count(n) From GoalLog n Where n.contentType = type AND n.parentId = parentId AND n.status In :statusList")
	Integer findNumByParentAndStatusIn(ContentType type, Long parentId, List<ContentStatus> contentStatusList);
}