package com.cyhee.rabit.dao.goallog;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.goallog.QGoalLog;
import com.querydsl.jpa.JPQLQuery;

public class GoalLogRepositoryImpl extends QuerydslRepositorySupport implements GoalLogRepositoryCustom {
	
	public GoalLogRepositoryImpl() {
		super(GoalLog.class);
	}

	@Override
	public Page<GoalLog> search(String keyword, List<ContentStatus> statusList, Pageable pageable) {
		QGoalLog goalLog = QGoalLog.goalLog;
		
		JPQLQuery<GoalLog> query = from(goalLog);
		query.where(goalLog.content.contains(keyword));
		query.where(goalLog.status.in(statusList));
		
		List<GoalLog> content = getQuerydsl().applyPagination(pageable, query).fetch();
		long total = query.fetchCount();
		return new PageImpl<>(content, pageable, total);		
	}

	
}
