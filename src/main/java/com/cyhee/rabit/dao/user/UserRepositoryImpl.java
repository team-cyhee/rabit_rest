package com.cyhee.rabit.dao.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.cyhee.rabit.model.user.QUser;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;
import com.querydsl.jpa.JPQLQuery;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

	public UserRepositoryImpl() {
		super(User.class);
	}

	@Override
	public Page<User> search(String keyword, List<UserStatus> statusList, Pageable pageable) {
		QUser user = QUser.user;
		
		JPQLQuery<User> query = from(user);
		query.where(user.username.contains(keyword));
		query.where(user.status.in(statusList));
		
		List<User> content = getQuerydsl().applyPagination(pageable, query).fetch();
		long total = query.fetchCount();
		return new PageImpl<>(content, pageable, total);
	}

}
