package com.cyhee.rabit.web.goallog;

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

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;

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
