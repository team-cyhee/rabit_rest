package com.cyhee.rabit.web.goal;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.user.UserService;

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
	    goalService.addGoal(author, author, goal);
        return ResponseHelper.createdEntity(ContentType.GOAL, goal.getId());
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
    
    @GetMapping(value = "/search")
	public ResponseEntity<Page<Goal>> search(@RequestParam String keyword, @PageableDefault Pageable pageable) {
		return new ResponseEntity<Page<Goal>>(goalService.search(keyword, ContentStatus.visible(), pageable), HttpStatus.OK);
	}
}
