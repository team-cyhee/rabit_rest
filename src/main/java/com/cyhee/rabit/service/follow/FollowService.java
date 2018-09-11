package com.cyhee.rabit.service.follow;

import com.cyhee.rabit.model.follow.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowService {
    Page<Follow> getFollows(Pageable pageable);

    void addFollow(Follow follow);

    Follow getFollow(long id);

    void updateFollow(long id, Follow followForm);

    void deleteFollow(long id);
}
