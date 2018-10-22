package com.cyhee.rabit.dao.user;

import java.util.List;
import java.util.Optional;

import com.cyhee.rabit.model.cmm.RadioStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Page<User> findAllByStatusIn(List<UserStatus> statusList, Pageable pageable);

	@Query("Select f.followee From Follow f Where :follower = f.follower AND f.status In :statusList")
	Page<User> findByFollowerAndStatusIn(@Param("follower") User follower, @Param("statusList")  List<RadioStatus> statusList, Pageable pageable);

	@Query("Select f.follower From Follow f Where :followee = f.followee AND f.status In :statusList")
	Page<User> findByFolloweeAndStatusIn(@Param("followee") User followee, @Param("statusList") List<RadioStatus> statusList, Pageable pageable);

}