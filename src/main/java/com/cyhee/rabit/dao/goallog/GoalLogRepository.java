
package com.cyhee.rabit.dao.goallog;

import com.cyhee.rabit.model.cmm.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GoalLogRepository extends PagingAndSortingRepository<GoalLog, Long> {
	Page<GoalLog> findByStatusNot(ContentStatus contentStatus, Pageable pageable);

	@Query("From GoalLog n Where :author = n.goal.author AND :status <> n.status")
	Page<GoalLog> findByAuthorAndStatusNot(@Param("author") User author, @Param("status") ContentStatus contentStatus, Pageable pageable);

	@Query("From GoalLog n Where :author = n.goal.author AND :status <> n.status")
	List<GoalLog> findByAuthorAndStatusNot(@Param("author") User author, @Param("status") ContentStatus contentStatus);

	Page<GoalLog> findByGoalAndStatusNot(Goal goal, ContentStatus contentStatus, Pageable pageable);

	List<GoalLog> findByGoalAndStatusNot(Goal goal, ContentStatus contentStatus);

}
