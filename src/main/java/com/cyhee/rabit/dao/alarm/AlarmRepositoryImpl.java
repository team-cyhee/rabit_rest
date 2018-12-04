package com.cyhee.rabit.dao.alarm;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.page.MainInfoBase;

public class AlarmRepositoryImpl extends QuerydslRepositorySupport implements AlarmRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;

	public AlarmRepositoryImpl() {
		super(Alarm.class);
	}
	
	public void tt(List<Long> userIds, Date from, Date to, Integer id) {
	}

	public List<MainInfoBase> mainBase(List<Long> userIds, Date from, Long order) {		
		String paging = "";
		if(order != null) paging = "id < " + order.toString() + " AND";
		
		Query q = em.createNativeQuery("" +
				"SELECT A.order, A.type, A.id " + 
				"FROM " + 
				"	(SELECT MAX(id) as order, " + 
				"		CASE  " + 
				"			WHEN action in ('GOAL', 'GOALLOG') THEN action " + 
				"			ELSE target " + 
				"		END as type, " + 
				"		CASE  " + 
				"			WHEN action in ('GOAL', 'GOALLOG') THEN action_id " + 
				"			ELSE target_id " + 
				"		END as id " + 
				"	FROM alarm " + 
				"	WHERE  " + 
						paging +
				"		create_date >= :from AND " + 
				"		((action in ('GOAL', 'GOALLOG') and author_id in :ids) or (action not in ('GOAL', 'GOALLOG') and target in ('GOAL', 'GOALLOG') and target_id in :ids)) " + 
				"	GROUP BY 2, 3) A " + 
				"	LEFT OUTER JOIN goal B on A.id = B.id " + 
				"	LEFT OUTER JOIN goal_log C on A.id = C.id " + 
				"	WHERE B.status <> :deleted or C.status <> :deleted " + 
				"	ORDER BY 1 DESC LIMIT 10"
		);
		
		q.setParameter("from", from);
		q.setParameter("ids", userIds);
		q.setParameter("deleted", ContentStatus.DELETED.ordinal());
		
		List<Object[]> result = q.getResultList();
		return result.stream().map(it -> mapper(it)).collect(Collectors.toList());
	}
	
	private MainInfoBase mapper(Object[] row) {
		if(row.length != 3)
			throw new IllegalArgumentException("Object[] must have 3 length, but it has " + row.length);
		
		Long order = ((BigInteger)row[0]).longValue();
		ContentType type = ContentType.valueOf((String)row[1]);
		Long id = ((BigInteger)row[2]).longValue();
		return new MainInfoBase(order, type, id);
	}
}
