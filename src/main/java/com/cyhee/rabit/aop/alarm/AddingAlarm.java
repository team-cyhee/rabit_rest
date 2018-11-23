package com.cyhee.rabit.aop.alarm;

import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.cmm.BaseEntity;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.alarm.AlarmService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AddingAlarm {
	@Autowired
	private AlarmService alarmService;

	@Around("@annotation(com.cyhee.rabit.aop.alarm.AddAlarm)")
	public Object onAfterReturningHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		Object [] args = joinPoint.getArgs();

		Object ret = joinPoint.proceed();

		User owner = (User)args[0];
		User author = (User)args[1];
		ContentType action = ContentType.findByKey(ret.getClass());
		Long actionId = ((BaseEntity)ret).getId();

		Alarm alarmTarget = new Alarm(owner, author, action, actionId);
		alarmService.addAlarm(alarmTarget);

		if (owner != author) {
			Alarm alarmAuthor = new Alarm(author, author, action, actionId);
			alarmService.addAlarm(alarmAuthor);
		}
		return ret;
	}
}
