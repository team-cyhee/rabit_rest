package com.cyhee.rabit.service.goal;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.GoalInfo;
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

        Page<Goal> goals = goalService.getGoals(pageable);

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
