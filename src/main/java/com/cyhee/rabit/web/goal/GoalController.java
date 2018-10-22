package com.cyhee.rabit.web.goal;

import javax.annotation.Resource;

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cyhee.rabit.model.goal.Goal;

import java.util.List;

@RestController
@RequestMapping("rest/v1/goals")
public class GoalController {
    @Resource(name="userService")
    private UserService userService;

	@Resource(name="goalService")
	private GoalService goalService;

	@Resource(name="goalStoreService")
    private GoalStoreService goalStoreService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Goal>> getGoals(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(goalService.getGoals(pageable), HttpStatus.OK);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<Goal>> getGoalsByUser() {
        User author = userService.getUserByUsername(AuthHelper.getUsername());
        return new ResponseEntity<>(goalService.getGoalsByAuthor(author), HttpStatus.OK);
    }

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addGoal(@RequestBody Goal goal) {
        User author = userService.getUserByUsername(AuthHelper.getUsername());
        goal.setAuthor(author);
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
    public ResponseEntity<Void> deleteGoal(@PathVariable long id, @PageableDefault Pageable pageable) {
    	goalStoreService.deleteGoal(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
