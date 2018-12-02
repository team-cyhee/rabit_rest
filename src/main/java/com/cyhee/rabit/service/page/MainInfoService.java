package com.cyhee.rabit.service.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.alarm.AlarmRepository;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.page.GoalInfo;
import com.cyhee.rabit.model.page.GoalLogInfo;
import com.cyhee.rabit.model.page.MainInfo;
import com.cyhee.rabit.model.page.MainInfoBase;
import com.cyhee.rabit.model.user.User;

@Service
public class MainInfoService {

	@Autowired
	GoalInfoService goalInfoService;

	@Autowired
	GoalLogInfoService goalLogInfoService;
	
	@Autowired
	AlarmRepository alarmRepository;

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
	
	public List<MainInfo> getMainInfos(List<Long> userIds, Date from, Long order) {
		List<MainInfoBase> baseList = this.getMainInfoBaseList(userIds, from, order);
		return baseList.stream().map(base -> {
			MainInfo info = null;
			if(base.getType() == ContentType.GOAL)
				info = goalInfoService.getGoalInfo(base.getId());
			else
				info =goalLogInfoService.getGoalLogInfo(base.getId());
			info.setOrder(base.getOrder());
			return info;
		}).collect(Collectors.toList());
	}
	
	private List<MainInfoBase> getMainInfoBaseList(List<Long> userIds, Date from, Long order) {
		return alarmRepository.mainBase(userIds, from, order);
	}
}
