package com.cyhee.rabit.service.alarm;

import com.cyhee.rabit.dao.alarm.AlarmRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.cmm.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("alarmService")
public class AlarmService {
	@Autowired
	private AlarmRepository alarmRepository;

	public Page<Alarm> getAlarms(Pageable pageable) {
		return alarmRepository.findAll(pageable);
	}

	public Page<Alarm> getUserAlarms(User owner, Pageable pageable) {
		return alarmRepository.findByOwnerOrAuthor(owner, pageable);
	}

	public Alarm getAlarm(long id) {
		Optional<Alarm> alarm = alarmRepository.findById(id);
		if(!alarm.isPresent())
			throw new NoSuchContentException(ContentType.ALARM, id);
		return alarm.get();
	}


	public void addAlarm(Alarm alarm) {
		alarmRepository.save(alarm);
	}
}
