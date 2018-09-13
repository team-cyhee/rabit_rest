package com.cyhee.rabit.service.follow;

import com.cyhee.rabit.dao.follow.FollowRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.follow.FollowStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("basicFollowService")
public class BasicFollowService implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Page<Follow> getFollows(Pageable pageable) {
        return followRepository.findAll(pageable);
    }

    public void addFollow(Follow follow) {
        followRepository.save(follow);
    }

    public Follow getFollow(long id) {
        Optional<Follow> follow = followRepository.findById(id);
        if (!follow.isPresent()) {
            throw new NoSuchContentException(ContentType.FOLLOW, id);
        }
        return follow.get();
    }

    public void updateFollow(long id, Follow followForm) {
        Follow follow = getFollow(id);
        setFollowProps(follow, followForm);
    }

    public void deleteFollow(long id) {
        Follow follow = getFollow(id);
        follow.setStatus(FollowStatus.INACTIVE);
    }

    private void setFollowProps(Follow target, Follow source) {
        target.setFollower(source.getFollower());
        target.setFollowee(source.getFollowee());
        target.setStatus(source.getStatus());
        target.setCreateDate(source.getCreateDate());
    }
}
