package com.cyhee.rabit.service.page;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.page.GoalInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.goal.CompanionService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("goalInfoService")
public class GoalInfoService {
    @Autowired
    GoalService goalService;

    @Autowired
    GoalStoreService goalStoreService;

    @Autowired
    CompanionService companionService;

    public List<GoalInfo> getGoalInfos(Pageable pageable) {
        List<GoalInfo> goalInfos = new ArrayList<>();

        Page<Goal> goals = goalService.getGoalsByStatusIn(ContentStatus.visible(), pageable);

        for (Goal goal : goals) {
            goalInfos.add(goalToGoalInfo(goal));
        }
        return goalInfos;
    }

    public List<GoalInfo> getGoalInfosByUser(User user, Pageable pageable) {
        List<GoalInfo> goalInfos = new ArrayList<>();

        Page<Goal> goals = goalService.getGoalsByAuthorStatusIn(user, ContentStatus.visible(), pageable);

        for (Goal goal : goals) {
            goalInfos.add(goalToGoalInfo(goal));
        }
        return goalInfos;
    }

    public GoalInfo getGoalInfo(Long id) {
        Goal goal = goalService.getGoal(id);
        return goalToGoalInfo(goal);
    }

    private GoalInfo goalToGoalInfo(Goal goal) {
        Integer logNum = goalStoreService.getLogNum(goal);
        Integer likeNum = goalStoreService.getLikeNum(goal);
        Integer commentNum = goalStoreService.getCommentNum(goal);
        Integer companionNum = companionService.getCompanionNum(goal);
        Page<Comment> comments = goalStoreService.getComments(goal, PageRequest.of(0, 2));
        return new GoalInfo(goal, logNum, likeNum, commentNum, companionNum, comments);
    }
}
