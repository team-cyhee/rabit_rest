package com.cyhee.rabit.dao.goal;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.GoalUnit;
import com.cyhee.rabit.model.goal.QGoal;
import com.querydsl.jpa.JPQLQuery;

public class GoalRepositoryImpl extends QuerydslRepositorySupport implements GoalRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;

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

	@Override
	public Double achievementRate(Goal goal, Date current) {
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		Query q = em.createNativeQuery("" +
				"SELECT kk/:times/("+dateDiff(goal.getDoUnit(), ":current", "A.start_date")+"+1) " + 
				"FROM " + 
				"	(SELECT * " + 
				"	FROM goal " + 
				"	WHERE id = :id) A, " + 
				"	(SELECT SUM(k) AS kk " + 
				"	FROM " + 
				"		(SELECT GREATEST("+dateDiff(goal.getDoUnit(), "B.create_date", "A.start_date")+",0), LEAST(COUNT(*),:times) AS k " + 
				"		FROM  " + 
				"			goal A JOIN goal_log B " + 
				"			ON A.id = B.goal_id AND A.id = :id AND B.status <> :deleted" + 
				"		GROUP BY GREATEST("+dateDiff(goal.getDoUnit(), "B.create_date", "A.start_date")+",0)) A) B");
		
		q.setParameter("id", goal.getId());
		q.setParameter("times", goal.getDoTimes());
		q.setParameter("current", current);
		q.setParameter("deleted", ContentStatus.DELETED.ordinal());
		
		return jpaResultMapper.list(q, Double.class).get(0);
	}
	
	private String dateDiff(GoalUnit gu, String from, String to) {
		switch(gu) {
			case DAILY:
				return "date_part('day', "+from+" - "+to+")";
			case WEEKLY:
				return "floor("+"date_part('day', "+from+" - "+to+")"+"/7)";
			case MONTHLY:
				return "extract(year from age("+from+", "+to+"))*12" + "extract(month from age("+from+", "+to+"))";
			case YEARLY:
				return "extract(year from age("+from+", "+to+"))";
		}
		return null;
	}
}
