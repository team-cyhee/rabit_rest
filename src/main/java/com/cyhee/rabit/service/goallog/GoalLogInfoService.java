package com.cyhee.rabit.service.goallog;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.goallog.GoalLogInfo;
import com.cyhee.rabit.service.goal.CompanionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service("goalLogInfoService")
public class GoalLogInfoService {
    @Autowired
    GoalLogService goalLogService;

    @Autowired
    GoalLogStoreService goalLogStoreService;

    @Autowired
    CompanionService companionService;

    public List<GoalLogInfo> getGoalLogInfos(Pageable pageable) {
        List<GoalLogInfo> goalLogInfos = new ArrayList<>();

        Page<GoalLog> goalLogs = goalLogService.getGoalLogs(pageable);

        for (GoalLog goalLog : goalLogs) {
            goalLogInfos.add(goalLogToGoalLogInfo(goalLog));
        }
        return goalLogInfos;
    }

    public GoalLogInfo getGoalLogInfo(Long id) {
        GoalLog goalLog = goalLogService.getGoalLog(id);
        return goalLogToGoalLogInfo(goalLog);
    }

    public GoalLogInfo goalLogToGoalLogInfo(GoalLog goalLog) {
        Integer likeNum = goalLogStoreService.getLikeNum(goalLog);
        Integer commentNum = goalLogStoreService.getCommentNum(goalLog);
        Integer companionNum = companionService.getCompanionNum(goalLog.getGoal());
        Page<Comment> comments = goalLogStoreService.getComments(goalLog, PageRequest.of(0, 2));
        return new GoalLogInfo(goalLog, likeNum, commentNum, companionNum, comments);
    }
}
