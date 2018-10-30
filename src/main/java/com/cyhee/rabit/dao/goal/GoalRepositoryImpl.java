package com.cyhee.rabit.dao.goal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.QGoal;
import com.querydsl.jpa.JPQLQuery;

public class GoalRepositoryImpl extends QuerydslRepositorySupport implements GoalRepositoryCustom {

	public GoalRepositoryImpl() {
		super(Goal.class);
	}

	@Override
	public Page<Goal> search(String keyword, List<ContentStatus> statusList, Pageable pageable) {
		QGoal goal = QGoal.goal;
		
		JPQLQuery<Goal> query = from(goal);
		query.where(goal.content.contains(keyword));
		query.where(goal.status.in(statusList));
		
		List<Goal> content = getQuerydsl().applyPagination(pageable, query).fetch();
		long total = query.fetchCount();
		return new PageImpl<>(content, pageable, total);
	}

}
