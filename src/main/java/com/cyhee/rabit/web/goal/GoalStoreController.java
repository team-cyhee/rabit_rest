package com.cyhee.rabit.web.goal;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.goallog.GoalLogDTO;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.page.GoalLogInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.file.FileService;
import com.cyhee.rabit.service.goal.CompanionService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.like.LikeService;
import com.cyhee.rabit.service.user.UserService;

@RestController
@RequestMapping("rest/v1/goals/{id}")
public class GoalStoreController {
	@Autowired
	private GoalService goalService;
	@Autowired
	private GoalStoreService goalStoreService;
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private GoalLogService goalLogService;
	@Autowired
	private CompanionService companionService;
	@Autowired
	private FileService fileService;
	
	@GetMapping("/companions")
	public ResponseEntity<Page<User>> getCompanions(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
		return ResponseEntity.ok(companionService.getCompanions(goal, pageable));
	}
	
	@GetMapping("/companion-goals")
	public ResponseEntity<Page<Goal>> getCompanionGoals(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
		return ResponseEntity.ok(companionService.getCompanionGoals(goal, pageable));
	}
	
	@PostMapping("/companion-goals")
	public ResponseEntity<Void> addCompanionGoal(@PathVariable Long id, @RequestBody Goal companionGoal) {
		Goal goal = goalService.getGoal(id);
		Goal root = goal.getParent();
		if(root == null) root = goal;
		
		User author = userService.getUserByUsername(AuthHelper.getUsername());
		companionGoal.setAuthor(author);
		companionGoal.setParent(root);
		goalService.addGoal(goal.getAuthor(), author, ContentType.GOAL, companionGoal);
        return ResponseHelper.createdEntity(ContentType.GOAL, companionGoal.getId());
	}	
	
	@GetMapping("/goallogs")
	public ResponseEntity<Page<GoalLog>> getGoalLogs(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
        return new ResponseEntity<>(goalStoreService.getGoalLogs(goal, ContentStatus.all(), pageable), HttpStatus.OK);
    }
	
	@PostMapping("/goallogs")
	public ResponseEntity<Void> addGoalLog(@PathVariable Long id, @RequestBody GoalLogDTO.PostOneFile dto) {
		Goal goal = goalService.getGoal(id);
    	
		GoalLog goalLog = new GoalLog();
    	goalLog.setGoal(goal);
    	goalLog.setContent(dto.getContent());
    	if(dto.getFileId() != null) goalLog.getFile().add(fileService.getFile(dto.getFileId()));
    	
    	goalLogService.addGoalLog(goal.getAuthor(), goal.getAuthor(), ContentType.GOAL, goalLog);
        return ResponseHelper.createdEntity(ContentType.GOALLOG, goalLog.getId());
	}

	@GetMapping("/goallogs/info")
	public ResponseEntity<List<GoalLogInfo>> getGoalLogInfos(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
		return new ResponseEntity<>(goalStoreService.getGoalLogInfos(goal, pageable), HttpStatus.OK);
	}
	
	@GetMapping("/comments")
	public ResponseEntity<Page<Comment>> getComments(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
        return new ResponseEntity<>(goalStoreService.getComments(goal, pageable), HttpStatus.OK);
    }
	
	@PostMapping("/comments")
	public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody Comment comment) {
		Goal goal = goalService.getGoal(id);
		
    	String username = AuthHelper.getUsername();
    	User author = userService.getUserByUsername(username);
    	
    	comment.setAuthor(author);
    	comment.setParentId(goal.getId());
    	comment.setType(ContentType.GOAL);
    	
    	commentService.addComment(goal.getAuthor(), author, ContentType.GOAL, comment);
		return ResponseHelper.createdEntity(ContentType.COMMENT, comment.getId());
	}

    @GetMapping("/likes")
	public ResponseEntity<Page<User>> getLikes(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Goal goal = goalService.getGoal(id);
		return new ResponseEntity<>(goalStoreService.getLikers(goal, pageable), HttpStatus.OK);
	}
    
    @PostMapping("/likes")
    public ResponseEntity<Void> addLike(@PathVariable Long id) {
		Goal goal = goalService.getGoal(id);
		
    	String username = AuthHelper.getUsername();
    	User liker = userService.getUserByUsername(username);
    	
    	Like like = likeService.addLike(goal.getAuthor(), liker, ContentType.GOAL, goal, liker);
        return ResponseHelper.createdEntity(ContentType.LIKE, like.getId());
	}
    
    @DeleteMapping("/likes")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
		Goal goal = goalService.getGoal(id);
		
    	String username = AuthHelper.getUsername();
    	User liker = userService.getUserByUsername(username);
    	
    	likeService.deleteLike(ContentType.GOAL, goal.getId(), liker);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
