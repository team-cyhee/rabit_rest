package com.cyhee.rabit.dao.goallog;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goallog.GoalLog;

public interface GoalLogRepositoryCustom {
	Page<GoalLog> search(String keyword, List<ContentStatus> statusList, Pageable pageable);
}
