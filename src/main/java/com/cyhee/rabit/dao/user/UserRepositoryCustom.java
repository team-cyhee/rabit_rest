package com.cyhee.rabit.dao.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;

public interface UserRepositoryCustom {
	Page<User> search(String keyword, List<UserStatus> statusList, Pageable pageable);
}
