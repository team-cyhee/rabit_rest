package com.cyhee.rabit.dao.follow;

import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface FollowRepository extends PagingAndSortingRepository<Follow, Long> {
    Page<Follow> findByStatusIn(List<RadioStatus> statusList, Pageable pageable);

    List<Follow> findByFollowerAndStatusIn(User follower, List<RadioStatus> statusList);

    @Query("Select count(f) From Follow f Where :follower = f.follower AND f.status In :statusList")
    Integer findNumByFollowerAndStatusIn(@Param("follower") User follower, @Param("statusList") List<RadioStatus> statusList);

    List<Follow> findByFolloweeAndStatusIn(User followee, List<RadioStatus> statusList);

    @Query("Select count(f) From Follow f Where :followee = f.followee AND f.status In :statusList")
    Integer findNumByFolloweeAndStatusIn(@Param("followee") User followee, @Param("statusList") List<RadioStatus> statusList);

    @Query("Select count(f) > 0 From Follow f Where :follower = f.follower AND :followee = f.followee AND f.status = com.cyhee.rabit.model.cmm.RadioStatus.ACTIVE")
    boolean existsByFollowerAndFollowee(@Param("follower") User follower, @Param("followee") User followee);
}
