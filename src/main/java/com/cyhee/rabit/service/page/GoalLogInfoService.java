package com.cyhee.rabit.service.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.page.GoalLogInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.goal.CompanionService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.like.LikeService;
import com.cyhee.rabit.service.user.UserService;

@Service("goalLogInfoService")
public class GoalLogInfoService {
    @Autowired
    GoalLogService goalLogService;

    @Autowired
    GoalLogStoreService goalLogStoreService;

    @Autowired
    CompanionService companionService;
    
    @Autowired
    LikeService likeService;
    
    @Autowired
    UserService userService;

    public List<GoalLogInfo> getGoalLogInfos(Pageable pageable) {
        List<GoalLogInfo> goalLogInfos = new ArrayList<>();

        Page<GoalLog> goalLogs = goalLogService.getGoalLogsByStatusIn(ContentStatus.visible(), pageable);

        for (GoalLog goalLog : goalLogs) {
            goalLogInfos.add(goalLogToGoalLogInfo(goalLog));
        }
        return goalLogInfos;
    }

    public List<GoalLogInfo> getGoalLogInfosByUser(User user, Pageable pageable) {
        List<GoalLogInfo> goalLogInfos = new ArrayList<>();

        Page<GoalLog> goalLogs = goalLogService.getGoalLogsByAuthorAndStatusIn(user, ContentStatus.visible(), pageable);

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
    	User user = userService.getUserByUsername(AuthHelper.getUsername());
        Integer likeNum = goalLogStoreService.getLikeNum(goalLog);
        Integer commentNum = goalLogStoreService.getCommentNum(goalLog);
        Integer companionNum = companionService.getCompanionNum(goalLog.getGoal());
        Page<Comment> comments = goalLogStoreService.getComments(goalLog, PageRequest.of(0, 2));
        boolean liked = likeService.existsByContentAndAuthor(ContentType.GOALLOG, goalLog.getId(), user);
        return new GoalLogInfo(goalLog, likeNum, commentNum, companionNum, comments, liked);
    }
}
