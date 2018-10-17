package com.cyhee.rabit.dao.goal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;

public interface CompanionRepository extends Repository<User, Long> {
	/*@Query("SELECT g.author FROM Goal g WHERE (g.parent = :root OR g = :root) AND g.status In :statusList")
	Page<User> findAllByGoal(@Param("root") Goal goal, @Param("statusList") List<ContentStatus> statusList, Pageable pageable);*/
}