package com.cyhee.rabit.dao.goal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;

public interface GoalRepositoryCustom {
	Page<Goal> search(String keyword, List<ContentStatus> statusList, Pageable pageable);
}
