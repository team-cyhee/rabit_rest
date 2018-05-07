package com.cyhee.rabit.user.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.user.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

}