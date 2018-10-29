package com.cyhee.rabit.service.page;

import java.util.ArrayList;
import java.util.List;

import com.cyhee.rabit.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.page.GoalInfo;
import com.cyhee.rabit.model.page.GoalLogInfo;
import com.cyhee.rabit.model.page.MainInfo;

@Service
public class MainInfoService {

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

	public List<MainInfo> getUserMainInfos(User user, Pageable pageable) {
		List<MainInfo> mainInfo = new ArrayList<>();

		List<GoalInfo> goalInfos = goalInfoService.getGoalInfosByUser(user, pageable);
		List<GoalLogInfo> goalLogInfos = goalLogInfoService.getGoalLogInfosByUser(user, pageable);

		mainInfo.addAll(goalInfos);
		mainInfo.addAll(goalLogInfos);

		mainInfo.sort(new MainInfo.DateSort());
		return mainInfo.subList(0, mainInfo.size());
	}
}
