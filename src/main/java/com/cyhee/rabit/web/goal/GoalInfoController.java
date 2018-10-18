package com.cyhee.rabit.web.goal;

import com.cyhee.rabit.model.goal.GoalInfo;
import com.cyhee.rabit.service.goal.GoalInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("rest/v1/goals/info")
public class GoalInfoController {
    @Resource(name="goalInfoService")
    private GoalInfoService goalInfoService;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<GoalInfo> getGoalInfo(@PathVariable long id) {
        return new ResponseEntity<>(goalInfoService.getGoalInfo(id), HttpStatus.OK);
    }
}