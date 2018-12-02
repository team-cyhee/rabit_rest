package com.cyhee.rabit.aop.alarm;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.cmm.BaseEntity;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.alarm.AlarmService;
import com.cyhee.rabit.service.cmm.ContentHelper;
import com.cyhee.rabit.service.cmm.ContentService;

@Aspect
@Component
public class AddingAlarm {
	@Autowired
	private AlarmService alarmService;
	@Autowired
	private ContentService contentService;

	@Around("@annotation(com.cyhee.rabit.aop.alarm.AddAlarm)")
	public Object onAfterReturningHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		Object ret = joinPoint.proceed();
		Pair<Long, ContentType> pair = ContentHelper.getParentPair(ret);

		User owner = contentService.getParentOwner(ret);
		User author = ContentHelper.getOwner(ret);
		ContentType target = null;
		Long targetId = null;
		if(pair != null) {
			target = pair.getSecond();
			targetId = pair.getFirst();
		}
		ContentType action = ContentType.findByKey(ret.getClass());
		Long actionId = ((BaseEntity)ret).getId();

		Alarm alarmTarget = new Alarm(owner, author, target, targetId, action, actionId);
		alarmService.addAlarm(alarmTarget);

		return ret;
	}
}
