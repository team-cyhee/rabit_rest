package com.cyhee.rabit.goal.store.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.comment.model.Comment;
import com.cyhee.rabit.goal.model.Goal;
import com.cyhee.rabit.goal.service.GoalService;
import com.cyhee.rabit.goal.store.service.GoalStoreService;
import com.cyhee.rabit.goallog.model.GoalLog;

@RestController
@RequestMapping("rest/v1/goals/{id}")
public class GoalStoreController {
	@Autowired
	private GoalService goalService;
	@Autowired
	private GoalStoreService goalStoreService;
	
	@GetMapping("/goallogs")
	public ResponseEntity<Page<GoalLog>> getGoalLogs(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
        return new ResponseEntity<>(goalStoreService.getGoalLogs(goal, pageable), HttpStatus.OK);
    }
	
	@GetMapping("/comments")
	public ResponseEntity<Page<Comment>> getComments(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
        return new ResponseEntity<>(goalStoreService.getComments(goal, pageable), HttpStatus.OK);
    }
}
