package com.cyhee.rabit.web.alarm;

import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.alarm.AlarmService;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("rest/v1/alarms")
public class AlarmController {
	@Resource(name="alarmService")
	private AlarmService alarmService;

	@Resource(name="userService")
    private UserService userService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Alarm>> getAlarms(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(alarmService.getAlarms(pageable), HttpStatus.OK);
    }

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addAlarm(@RequestBody Alarm alarm) {
        alarmService.addAlarm(alarm);
        return ResponseHelper.createdEntity(ContentType.ALARM, alarm.getId());
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Alarm> getAlarm(@PathVariable long id) {
    	return new ResponseEntity<>(alarmService.getAlarm(id), HttpStatus.OK);
    }

    @RequestMapping(value="/user", method=RequestMethod.GET)
    public ResponseEntity<Page<Alarm>> getUserAlarms(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        User owner = userService.getUserByUsername(AuthHelper.getUsername());
        return new ResponseEntity<>(alarmService.getUserAlarms(owner, pageable), HttpStatus.OK);
    }
}
