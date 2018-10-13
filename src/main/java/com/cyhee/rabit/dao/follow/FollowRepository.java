package com.cyhee.rabit.dao.follow;

import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface FollowRepository extends PagingAndSortingRepository<Follow, Long> {
    Page<Follow> findByStatusIn(List<RadioStatus> statusList, Pageable pageable);

    Page<Follow> findByFollowerAndStatusIn(User follower, List<RadioStatus> statusList, Pageable pageable);

    List<Follow> findByFollowerAndStatusIn(User follower, List<RadioStatus> statusList);

    Page<Follow> findByFolloweeAndStatusIn(User followee, List<RadioStatus> statusList, Pageable pageable);

    List<Follow> findByFolloweeAndStatusIn(User followee, List<RadioStatus> statusList);
}
