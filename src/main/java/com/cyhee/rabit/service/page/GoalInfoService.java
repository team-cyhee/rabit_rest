package com.cyhee.rabit.service.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.page.GoalInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.goal.CompanionService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.like.LikeService;
import com.cyhee.rabit.service.user.UserService;

@Service("goalInfoService")
public class GoalInfoService {
    @Autowired
    GoalService goalService;

    @Autowired
    GoalStoreService goalStoreService;

    @Autowired
    CompanionService companionService;
    
    @Autowired
    LikeService likeService;
    
    @Autowired
    UserService userService;

    public List<GoalInfo> getGoalInfos(Pageable pageable) {
        List<GoalInfo> goalInfos = new ArrayList<>();

        Page<Goal> goals = goalService.getGoalsByStatusIn(ContentStatus.visible(), pageable);

        for (Goal goal : goals) {
            goalInfos.add(goalToGoalInfo(goal));
        }
        return goalInfos;
    }

    public List<GoalInfo> getGoalInfosByUser(User user, Date from, Pageable pageable) {
        List<GoalInfo> goalInfos = new ArrayList<>();

        Page<Goal> goals = goalService.getGoalsByAuthorAndCreateDateLessThanAndStatusIn(user, from, ContentStatus.visible(), pageable);

        for (Goal goal : goals) {
            goalInfos.add(goalToGoalInfo(goal));
        }
        return goalInfos;
    }

    public GoalInfo getGoalInfo(Long id) {
        Goal goal = goalService.getGoal(id);
        return goalToGoalInfo(goal);
    }

    public GoalInfo getGoalInfoWithRate(Long id) {
        Goal goal = goalService.getGoal(id);
        GoalInfo goalInfo = goalToGoalInfo(goal);
        goalInfo.setAchievementRate(goalStoreService.achievementRate(goal));
        return goalInfo;
    }

    private GoalInfo goalToGoalInfo(Goal goal) {
    	User user = userService.getUserByUsername(AuthHelper.getUsername());
        Integer logNum = goalStoreService.getLogNum(goal);
        Integer likeNum = goalStoreService.getLikeNum(goal);
        Integer commentNum = goalStoreService.getCommentNum(goal);
        Integer companionNum = companionService.getCompanionNum(goal);
        boolean liked = likeService.existsByContentAndAuthor(ContentType.GOAL, goal.getId(), user);
        return new GoalInfo(goal, logNum, likeNum, commentNum, companionNum, liked, 0.0);
    }
}
