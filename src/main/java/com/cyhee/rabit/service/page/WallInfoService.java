package com.cyhee.rabit.service.page;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.service.goallog.GoalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.page.WallInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;

@Service("wallInfoService")
public class WallInfoService {

    @Autowired
    private UserStoreService userStoreService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private GoalLogService goalLogService;
    
    @Autowired
    private FollowService followService;

    public WallInfo getWallInfos(User user) {
        Integer followerNum = userStoreService.getFollowerNum(user);
        Integer followeeNum = userStoreService.getFolloweeNum(user);
        Integer goalLogNum = goalLogService.getGoalLogNumByAuthorAndStatusIn(user, ContentStatus.visible());
        boolean self = user.getUsername().equals(AuthHelper.getUsername());
        boolean following = followService.exists(userService.getUserByUsername(AuthHelper.getUsername()), user);

        return new WallInfo(user.getId(), user.getUsername(), followerNum, followeeNum, goalLogNum, following, self);
    }
}
