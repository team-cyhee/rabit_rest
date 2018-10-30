package com.cyhee.rabit.service.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;
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
    private GoalService goalService;
    
    @Autowired
    private FollowService followService;

    public WallInfo getWallInfos(User user) {
        Integer followerNum = userStoreService.getFollowerNum(user);
        Integer followeeNum = userStoreService.getFolloweeNum(user);
        boolean self = user.getUsername().equals(AuthHelper.getUsername());
        boolean following = followService.exists(userService.getUserByUsername(AuthHelper.getUsername()), user);

        Page<Goal> goals = goalService.getGoalsByAuthorStatusIn(user, ContentStatus.visible(), PageRequest.of(0, 3));
        List<String> goalContents = new ArrayList<>();
        for (Goal g : goals) {
            goalContents.add(g.getContent());
        }

        return new WallInfo(user.getId(), user.getUsername(), followerNum, followeeNum, goalContents, following, self);
    }
}
