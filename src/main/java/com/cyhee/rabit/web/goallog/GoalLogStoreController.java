package com.cyhee.rabit.web.goallog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.like.LikeService;
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
	@Autowired
	private LikeService likeService;
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/comments")
	public ResponseEntity<Page<Comment>> getComments(@PathVariable Long id, @PageableDefault Pageable pageable) {
		GoalLog goalLog = goalLogService.getGoalLog(id);
        return new ResponseEntity<>(goalLogStoreService.getComments(goalLog, pageable), HttpStatus.OK);
    }
	
	@PostMapping("/comments")
	public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody Comment comment) {
		GoalLog goalLog = goalLogService.getGoalLog(id);
		
    	String username = AuthHelper.getUsername();
    	User author = userService.getUserByUsername(username);
    	
    	comment.setAuthor(author);
    	comment.setParentId(goalLog.getId());
    	comment.setType(ContentType.GOALLOG);
    	
    	commentService.addComment(comment);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/likes")
	public ResponseEntity<Page<User>> getLikes(@PathVariable Long id, @PageableDefault Pageable pageable) {
		GoalLog goalLog = goalLogService.getGoalLog(id);
		return new ResponseEntity<>(goalLogStoreService.getLikers(goalLog, pageable), HttpStatus.OK);
	}
    
    @PostMapping("/likes")
    public ResponseEntity<Void> addLike(@PathVariable Long id) {
		GoalLog goalLog = goalLogService.getGoalLog(id);
		
    	String username = AuthHelper.getUsername();
    	User liker = userService.getUserByUsername(username);
    	likeService.addLike(goalLog, liker);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
