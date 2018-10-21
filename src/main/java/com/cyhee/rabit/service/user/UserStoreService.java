package com.cyhee.rabit.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.dao.follow.FollowRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;

@Service
public class UserStoreService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private GoalService goalService;
	
	@Autowired
	private GoalLogService goalLogService;

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private GoalStoreService goalStoreService;

	@Autowired
	private GoalLogStoreService goalLogStoreService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	private FollowService followService;

	public void deleteUser(Long id) {
		User user = userService.getUser(id);
		
		AuthHelper.isAuthorOrAdmin(user);
		
		userService.delete(user);
		deleteAllUserStore(user);
	}
	
	public List<Goal> getGoals(User author) {
		return goalService.getGoalsByAuthor(author);
	}

	public Page<Goal> getGoals(User author, List<ContentStatus> statusList, Pageable pageable) {
		return goalService.getGoalsByAuthorStatusIn(author, statusList, pageable);
	}

	public List<GoalLog> getGoalLogs(User author) {
		return goalLogService.getGoalLogsByAuthor(author);
	}
	
	public Page<GoalLog> getGoalLogs(User author, List<ContentStatus> statusList, Pageable pageable) {
		return goalLogService.getGoalLogsByAuthorAndStatusIn(author, statusList, pageable);
	}

	public Page<Comment> getComments(Goal goal, Pageable pageable) {
		return commentRepository.findByTypeAndParentIdAndStatusIn(ContentType.USER, goal.getId(), ContentStatus.visible(), pageable);
	}

	public List<Comment> getComments(Goal goal) {
		return commentRepository.findByTypeAndParentIdAndStatusIn(ContentType.USER, goal.getId(), ContentStatus.visible());
	}

	public Page<Follow> getFollowers(User followee, Pageable pageable) {		
		return followRepository.findByFolloweeAndStatusIn(followee, RadioStatus.visible(), pageable);
	}

	public List<Follow> getFollowers(User followee) {
		return followRepository.findByFolloweeAndStatusIn(followee, RadioStatus.visible());
	}

	public Page<Follow> getFollowees(User follower, Pageable pageable) {
		return followRepository.findByFollowerAndStatusIn(follower, RadioStatus.visible(), pageable);
	}

	public List<Follow> getFollowees(User follower) {
		return followRepository.findByFollowerAndStatusIn(follower, RadioStatus.visible());
	}

	private void deleteAllUserStore(User user) {
		goalStoreService.deleteByParent(user);
		goalLogStoreService.deleteByParent(user);
		followService.deleteByParent(user);
	}
}
