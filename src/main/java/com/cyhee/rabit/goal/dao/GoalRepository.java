package com.cyhee.rabit.goal.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.goal.model.Goal;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GoalRepository extends PagingAndSortingRepository<Goal, Long> {
}