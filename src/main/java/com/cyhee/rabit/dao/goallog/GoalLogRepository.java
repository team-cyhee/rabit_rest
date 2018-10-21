
package com.cyhee.rabit.dao.goallog;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GoalLogRepository extends PagingAndSortingRepository<GoalLog, Long> {
	Page<GoalLog> findAllByStatusIn(List<ContentStatus> statusList, Pageable pageable);

	@Query("From GoalLog n Where :author = n.goal.author")
	List<GoalLog> findAllByAuthor(@Param("author") User author);
	
	@Query("From GoalLog n Where :author = n.goal.author AND n.status In :statusList")
	Page<GoalLog> findAllByAuthorAndStatusIn(@Param("author") User author, @Param("statusList") List<ContentStatus> statusList, Pageable pageable);

	List<GoalLog> findAllByGoal(Goal goal);

	Page<GoalLog> findAllByGoalAndStatusIn(Goal goal, List<ContentStatus> statusList, Pageable pageable);

	@Query("Select count(gl) From GoalLog gl Where :goal = gl.goal AND gl.status In :statusList")
	Integer findNumByGoalAndStatusIn(@Param("goal")Goal goal, @Param("statusList")List<ContentStatus> statusList);
}
