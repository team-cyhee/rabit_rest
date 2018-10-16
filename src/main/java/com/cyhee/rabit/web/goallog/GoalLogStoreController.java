package com.cyhee.rabit.web.goallog;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.user.UserService;

@RestController
@RequestMapping("rest/v1/goallogs/{id}")
public class GoalLogStoreController {
	@Autowired
	private GoalLogService goalLogService;
	@Autowired
	private GoalLogStoreService goalLogStoreService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/comments")
	public ResponseEntity<Page<Comment>> getComments(@PathVariable Long id, @PageableDefault Pageable pageable) {
		GoalLog goalLog = goalLogService.getGoalLog(id);
        return new ResponseEntity<>(goalLogStoreService.getComments(goalLog, pageable), HttpStatus.OK);
    }

	@GetMapping("/likes")
	public ResponseEntity<Page<Like>> getLikes(@PathVariable Long id, @PageableDefault Pageable pageable) {
		GoalLog goalLog = goalLogService.getGoalLog(id);
		return new ResponseEntity<>(goalLogStoreService.getLikes(goalLog, pageable), HttpStatus.OK);
	}
    
    @PostMapping("/likes")
    public ResponseEntity<Void> getLikes(@PathVariable Long id, Principal principal) {
    	String username = principal.getName();
    	User liker = userService.getUserByUsername(username);
		GoalLog goalLog = goalLogService.getGoalLog(id);
		goalLogStoreService.addLike(goalLog, liker);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
