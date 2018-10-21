package com.cyhee.rabit.service.main;

import java.util.ArrayList;
import java.util.List;

import com.cyhee.rabit.service.goal.GoalInfoService;
import com.cyhee.rabit.service.goallog.GoalLogInfoService;
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
	GoalInfoService goalInfoService;

	@Autowired
	GoalLogInfoService goalLogInfoService;

	public List<MainInfo> getMainInfos(Pageable pageable) {

		List<MainInfo> mainInfo = new ArrayList<>();

		List<GoalInfo> goalInfos = goalInfoService.getGoalInfos(pageable);
		List<GoalLogInfo> goalLogInfos = goalLogInfoService.getGoalLogInfos(pageable);

		mainInfo.addAll(goalInfos);
		mainInfo.addAll(goalLogInfos);

		mainInfo.sort(new MainInfo.DateSort());
		return mainInfo.subList(0, mainInfo.size());
	}
}
