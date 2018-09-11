
package com.cyhee.rabit.web.goallog;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.service.goallog.GoalLogService;

@RestController
@RequestMapping("rest/v1/goallogs")
public class GoalLogController {
	@Resource(name="basicGoalLogService")
	private GoalLogService goallogService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Iterable<GoalLog>> getGoalLogs(@PageableDefault Pageable pageable) {
		Page<GoalLog> logPage = goallogService.getGoalLogs(pageable);
        return new ResponseEntity<Iterable<GoalLog>>(logPage.getContent(), HttpStatus.OK);
    }
	
	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addGoalLog(@RequestBody GoalLog goallog) {
    	goallogService.addGoalLog(goallog);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<GoalLog> getGoalLog(@PathVariable long id) {
    	return new ResponseEntity<>(goallogService.getGoalLog(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updateGoalLog(@PathVariable long id, @RequestBody GoalLog goallogForm) {
    	goallogService.updateGoalLog(id, goallogForm);
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGoalLog(@PathVariable long id) {
    	goallogService.deleteGoalLog(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
