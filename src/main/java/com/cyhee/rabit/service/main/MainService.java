package com.cyhee.rabit.service.main;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.factory.GoalInfo;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.main.MainInfo;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

	@Autowired
	GoalLogService goalLogService;

	@Autowired
	GoalLogStoreService goalLogStoreService;

	public List<MainInfo> getMainInfos(Pageable pageable) {
		Page<GoalLog> goalLogs = goalLogService.getGoalLogs(pageable);
		List<MainInfo> mainInfo = new ArrayList<>();
		for (GoalLog goalLog : goalLogs) {
			Integer likeNum = goalLogStoreService.getLikeNum(goalLog);
			Integer commentNum = goalLogStoreService.getCommentNum(goalLog);
			Page<Comment> comments = goalLogStoreService.getComments(goalLog, PageRequest.of(1, 2));
			mainInfo.add(new GoalInfo(goalLog, likeNum, commentNum, comments));
		}
		return mainInfo;
	}
}
