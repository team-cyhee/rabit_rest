package com.cyhee.rabit.goallog.store.web;

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
import com.cyhee.rabit.goallog.model.GoalLog;
import com.cyhee.rabit.goallog.service.GoalLogService;
import com.cyhee.rabit.goallog.store.service.GoalLogStoreService;

@RestController
@RequestMapping("rest/v1/goallogs/{id}")
public class GoalLogStoreController {
	@Autowired
	private GoalLogService goalLogService;
	@Autowired
	private GoalLogStoreService goalLogStoreService;
	
	@GetMapping("/comments")
	public ResponseEntity<Page<Comment>> getComments(@PathVariable Long id, @PageableDefault Pageable pageable) {
		GoalLog goalLog = goalLogService.getGoalLog(id);
        return new ResponseEntity<>(goalLogStoreService.getComments(goalLog, pageable), HttpStatus.OK);
    }
}
