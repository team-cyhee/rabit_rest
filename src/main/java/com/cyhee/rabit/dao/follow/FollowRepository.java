package com.cyhee.rabit.dao.follow;

import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface FollowRepository extends PagingAndSortingRepository {
    Page<Follow> findByFollowerAndFollowing(User follower, User following, Pageable pageable);
}
