package com.cyhee.rabit.goal.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.goal.model.Goal;
import com.cyhee.rabit.goal.service.GoalService;
import com.cyhee.rabit.web.model.ApiResponseEntity;

@RestController
@RequestMapping("rest/v1/goals")
public class GoalController {
	@Resource(name="basicGoalService")
	private GoalService goalService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ApiResponseEntity<Iterable<Goal>> getAllGoals() {
        return new ApiResponseEntity<Iterable<Goal>>(goalService.getAllGoals(), HttpStatus.OK);
    }
	
	@RequestMapping(method=RequestMethod.POST)
    public ApiResponseEntity<Void> addGoal(@RequestBody Goal goal) {    	
    	goalService.addGoal(goal);
        return new ApiResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ApiResponseEntity<Goal> getGoal(@PathVariable long id) {
    	return new ApiResponseEntity<>(goalService.getGoal(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ApiResponseEntity<Void> updateGoal(@PathVariable long id, @RequestBody Goal goalForm) {
    	goalService.updateGoal(id, goalForm);
        return new ApiResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ApiResponseEntity<Void> deleteGoal(@PathVariable long id) {
    	goalService.deleteGoal(id);
        return new ApiResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
