package com.cyhee.rabit.web.goallog;

import com.cyhee.rabit.model.goallog.GoalLogInfo;
import com.cyhee.rabit.service.goallog.GoalLogInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("rest/v1/goallogs/info")
public class GoalLogInfoController {
    @Resource(name="goalLogInfoService")
    private GoalLogInfoService goalLogInfoService;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<GoalLogInfo> getGoalLogInfo(@PathVariable long id) {
        return new ResponseEntity<>(goalLogInfoService.getGoalLogInfo(id), HttpStatus.OK);
    }
}