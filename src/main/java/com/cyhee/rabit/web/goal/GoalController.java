package com.cyhee.rabit.web.goal;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.service.goal.GoalService;

@RestController
@RequestMapping("rest/v1/goals")
public class GoalController {
	@Resource(name="basicGoalService")
	private GoalService goalService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Iterable<Goal>> getGoals(@PageableDefault Pageable pageable) {
        return new ResponseEntity<Iterable<Goal>>(goalService.getGoals(pageable), HttpStatus.OK);
    }
	
	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addGoal(@RequestBody Goal goal) {    	
    	goalService.addGoal(goal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Goal> getGoal(@PathVariable long id) {
    	return new ResponseEntity<>(goalService.getGoal(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updateGoal(@PathVariable long id, @RequestBody Goal goalForm) {
    	goalService.updateGoal(id, goalForm);
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGoal(@PathVariable long id) {
    	goalService.deleteGoal(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
