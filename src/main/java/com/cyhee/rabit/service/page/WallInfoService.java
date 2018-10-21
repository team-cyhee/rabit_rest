package com.cyhee.rabit.service.page;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.page.WallInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.user.UserStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("wallInfoService")
public class WallInfoService {

    @Autowired
    private UserStoreService userStoreService;

    @Autowired
    private GoalService goalService;

    public WallInfo getWallInfos(User user) {
        Integer followerNum = userStoreService.getFollowerNum(user);
        Integer followeeNum = userStoreService.getFolloweeNum(user);

        Page<Goal> goals = goalService.getGoalsByAuthorStatusIn(user, ContentStatus.visible(), PageRequest.of(0, 3));
        List<String> goalContents = new ArrayList<>();
        for (Goal g : goals) {
            goalContents.add(g.getContent());
        }

        return new WallInfo(user.getId(), user.getUsername(), followerNum, followeeNum, goalContents);
    }
}
