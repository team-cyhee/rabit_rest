package com.cyhee.rabit.dao.goal;

import com.cyhee.rabit.model.cmm.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called goalRepository
// CRUD refers Create, Read, Update, Delete

public interface GoalRepository extends PagingAndSortingRepository<Goal, Long> {
	Page<Goal> findAllByStatusIn(List<ContentStatus> statusList, Pageable pageable);

	List<Goal> findAllByAuthor(User author);

	Page<Goal> findAllByAuthorAndStatusIn(User author, List<ContentStatus> statusList, Pageable pageable);
}