
package com.cyhee.rabit.goallog.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.cmm.web.model.ApiResponseEntity;
import com.cyhee.rabit.goallog.model.GoalLog;
import com.cyhee.rabit.goallog.service.GoalLogService;

@RestController
@RequestMapping("rest/v1/goallogs")
public class GoalLogController {
	@Resource(name="basicGoalLogService")
	private GoalLogService goallogService;
	
	/*@RequestMapping(method=RequestMethod.GET)
	public ApiResponseEntity<Iterable<GoalLog>> getAllGoalLogs() {
        return new ApiResponseEntity<Iterable<GoalLog>>(goallogService.getAllGoalLogs(), HttpStatus.OK);
    }
	
	@RequestMapping(method=RequestMethod.POST)
    public ApiResponseEntity<Void> addGoalLog(@RequestBody GoalLog goallog) {
    	goallogService.addGoalLog(goallog);
        return new ApiResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ApiResponseEntity<GoalLog> getGoalLog(@PathVariable long id) {
    	return new ApiResponseEntity<>(goallogService.getGoalLog(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ApiResponseEntity<Void> updateGoalLog(@PathVariable long id, @RequestBody GoalLog goallogForm) {
    	goallogService.updateGoalLog(id, goallogForm);
        return new ApiResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ApiResponseEntity<Void> deleteGoalLog(@PathVariable long id) {
    	goallogService.deleteGoalLog(id);
        return new ApiResponseEntity<Void>(HttpStatus.ACCEPTED);
    }*/
}
