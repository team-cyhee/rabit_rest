package com.cyhee.rabit.dao.user;

import java.util.Optional;

import com.cyhee.rabit.model.user.UserStatus;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.model.user.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByEmailAndStatusNot(String email, UserStatus userStatus);
	
	Optional<User> findByUsernameAndStatusNot(String username, UserStatus userStatus);
}