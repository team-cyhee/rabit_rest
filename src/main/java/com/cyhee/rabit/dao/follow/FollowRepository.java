package com.cyhee.rabit.dao.follow;

import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.follow.FollowStatus;
import com.cyhee.rabit.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface FollowRepository extends PagingAndSortingRepository<Follow, Long> {
    Page<Follow> findByStatusNot(FollowStatus notStatus, Pageable pageable);

    Page<Follow> findByFollowerAndStatusNot(User follower, FollowStatus notStatus, Pageable pageable);

    List<Follow> findByFollowerAndStatusNot(User follower, FollowStatus notStatus);

    Page<Follow> findByFolloweeAndStatusNot(User followee, FollowStatus notStatus, Pageable pageable);

    List<Follow> findByFolloweeAndStatusNot(User followee, FollowStatus notStatus);
}
