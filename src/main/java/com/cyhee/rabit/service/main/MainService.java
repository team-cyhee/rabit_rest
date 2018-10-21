package com.cyhee.rabit.service.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.GoalInfo;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.goallog.GoalLogInfo;
import com.cyhee.rabit.model.main.MainInfo;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;

@Service
public class MainService {

	@Autowired
	GoalService goalService;

	@Autowired
	GoalStoreService goalStoreService;

	@Autowired
	GoalLogService goalLogService;

	@Autowired
	GoalLogStoreService goalLogStoreService;

	public List<MainInfo> getMainInfos(Pageable pageable) {

		List<MainInfo> mainInfo = new ArrayList<>();

		Page<GoalLog> goalLogs = goalLogService.getGoalLogs(pageable);
		for (GoalLog goalLog : goalLogs) {
			Integer likeNum = goalLogStoreService.getLikeNum(goalLog);
			Integer commentNum = goalLogStoreService.getCommentNum(goalLog);
			Page<Comment> comments = goalLogStoreService.getComments(goalLog, PageRequest.of(1, 2));
			mainInfo.add(new GoalLogInfo(goalLog, likeNum, commentNum, comments));
		}

		Page<Goal> goals = goalService.getGoals(pageable);
		for (Goal goal : goals) {
			Integer logNum = goalStoreService.getLogNum(goal);
			Integer likeNum = goalStoreService.getLikeNum(goal);
			Integer commentNum = goalStoreService.getCommentNum(goal);
			Page<Comment> comments = goalStoreService.getComments(goal, PageRequest.of(1, 2));
			mainInfo.add(new GoalInfo(goal, logNum, likeNum, commentNum, comments));
		}
		mainInfo.sort(new MainInfo.DateSort());
		return mainInfo.subList(0, mainInfo.size());
	}
}
