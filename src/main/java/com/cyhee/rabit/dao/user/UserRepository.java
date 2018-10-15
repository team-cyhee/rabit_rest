package com.cyhee.rabit.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Page<User> findAllByStatusIn(List<UserStatus> statusList, Pageable pageable);
}