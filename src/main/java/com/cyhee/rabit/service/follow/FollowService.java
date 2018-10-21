package com.cyhee.rabit.service.follow;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.follow.FollowRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;

@Service("followService")
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Page<Follow> getFollows(Pageable pageable) {
        return followRepository.findByStatusIn(RadioStatus.visible(), pageable);
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
		
		AuthHelper.isAuthorOrAdmin(follow);
		
        setFollowProps(follow, followForm);
        followRepository.save(follow);
    }

    public void deleteFollow(long id) {
        Follow follow = getFollow(id);
		
		AuthHelper.isAuthorOrAdmin(follow);
		
        delete(follow);
    }
    
    public void deleteByParent(User parent) {
    	List<Follow> followees = followRepository.findByFolloweeAndStatusIn(parent, RadioStatus.all());
    	List<Follow> followers = followRepository.findByFollowerAndStatusIn(parent, RadioStatus.all());
    	
    	for(Follow follow : followers) delete(follow);
    	for(Follow follow : followees) delete(follow);
    }

    private void setFollowProps(Follow target, Follow source) {
        target.setStatus(source.getStatus());
    }
    
    void delete(Follow follow) {
    	follow.setStatus(RadioStatus.INACTIVE);
        followRepository.save(follow);
    }
}
