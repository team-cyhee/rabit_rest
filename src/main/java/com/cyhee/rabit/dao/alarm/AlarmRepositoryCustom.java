package com.cyhee.rabit.dao.alarm;

import java.util.Date;
import java.util.List;

import com.cyhee.rabit.model.page.MainInfoBase;

public interface AlarmRepositoryCustom {
	void tt(List<Long> userIds, Date from, Date to, Integer id);
	List<MainInfoBase> mainBase(List<Long> userIds, Date from, Long order);
}
