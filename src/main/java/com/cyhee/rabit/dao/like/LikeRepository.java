
package com.cyhee.rabit.dao.like;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface LikeRepository extends PagingAndSortingRepository<Like, Long> {
	Page<Like> findByTypeAndParentIdAndStatusIn(ContentType type, Long parentId, List<RadioStatus> statusList, Pageable pageable);

	List<Like> findByTypeAndParentIdAndStatusIn(ContentType type, Long parentId, List<RadioStatus> statusList);

	Page<Like> findByStatusIn(List<RadioStatus> statusList, Pageable pageable);

	@Query("Select l.author From Like l Where l.type = :cType AND l.parentId = :parentId AND l.status In :statusList")
	Page<User> findLikers(@Param("cType") ContentType cType, @Param("parentId") Long parentId, @Param("statusList") List<RadioStatus> statusList, Pageable pageable);

	@Query("Select count(n) From Like n Where :cType = n.type AND n.parentId = :parentId AND n.status In :statusList")
	Integer findNumByParentIdAndStatusIn(@Param("cType") ContentType cType, @Param("parentId") Long parentId, @Param("statusList") List<RadioStatus> statusList);
	
	@Query("Select count(l) > 0 From Like l Where :cType = l.type AND l.parentId = :parentId AND l.author = :user AND l.status = com.cyhee.rabit.model.cmm.RadioStatus.ACTIVE")
	boolean existsByContentAndAuthor(@Param("cType") ContentType cType, @Param("parentId") Long parentId, @Param("user") User author);
	
	@Query("Select l From Like l Where :cType = l.type AND l.parentId = :parentId AND l.author = :user AND l.status = com.cyhee.rabit.model.cmm.RadioStatus.ACTIVE")
	Optional<Like> findByContentAndAuthor(@Param("cType") ContentType cType, @Param("parentId") Long parentId, @Param("user") User author);
}
