
package com.cyhee.rabit.goallog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.goallog.model.GoalLog;
import com.cyhee.rabit.user.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GoalLogRepository extends PagingAndSortingRepository<GoalLog, Long> {
	Page<GoalLog> findAllByAuthor(User author, Pageable pageable);
}
