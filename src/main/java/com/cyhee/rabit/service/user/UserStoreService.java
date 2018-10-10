package com.cyhee.rabit.service.user;

import com.cyhee.rabit.dao.comment.CommentRepository;
import com.cyhee.rabit.dao.follow.FollowRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.goal.GoalRepository;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.dao.goallog.GoalLogRepository;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

import java.util.List;

@Service
public class UserStoreService {
	@Autowired
	private UserService userService;

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private GoalLogRepository goalLogRepository;

	@Autowired
	private GoalStoreService goalStoreService;

	@Autowired
	private GoalLogStoreService goalLogStoreService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	private FollowService followService;

	public void deleteUser(Long id) {
		User user = userService.deleteUser(id);
		deleteAllUserStore(user);
	}

	public void deleteUserByUsername(Long id) {
		User user = userService.deleteUser(id);
		deleteAllUserStore(user);
	}

	public Page<Goal> getGoals(User author, Pageable pageable) {
		return goalRepository.findByAuthorAndStatusNot(author, ContentStatus.DELETED, pageable);
	}

	public List<Goal> getGoals(User author) {
		return goalRepository.findByAuthorAndStatusNot(author, ContentStatus.DELETED);
	}
	
	public Page<GoalLog> getGoalLogs(User author, Pageable pageable) {
		return goalLogRepository.findByAuthorAndStatusNot(author, ContentStatus.DELETED, pageable);
	}

	public List<GoalLog> getGoalLogs(User author) {
		return goalLogRepository.findByAuthorAndStatusNot(author, ContentStatus.DELETED);
	}

	public Page<Comment> getComments(Goal goal, Pageable pageable) {
		return commentRepository.findByTypeAndParentIdAndStatusNot(ContentType.USER, goal.getId(), ContentStatus.DELETED, pageable);
	}

	public List<Comment> getComments(Goal goal) {
		return commentRepository.findByTypeAndParentIdAndStatusNot(ContentType.USER, goal.getId(), ContentStatus.DELETED);
	}

	public Page<Follow> getFollowers(User followee, Pageable pageable) {
		return followRepository.findByFolloweeAndStatusNot(followee, RadioStatus.INACTIVE, pageable);
	}

	public List<Follow> getFollowers(User followee) {
		return followRepository.findByFolloweeAndStatusNot(followee, RadioStatus.INACTIVE);
	}

	public Page<Follow> getFollowees(User follower, Pageable pageable) {
		return followRepository.findByFollowerAndStatusNot(follower, RadioStatus.INACTIVE, pageable);
	}

	public List<Follow> getFollowees(User follower) {
		return followRepository.findByFollowerAndStatusNot(follower, RadioStatus.INACTIVE);
	}

	public void deleteAllUserStore(User user) {
		for (Goal g : getGoals(user)) {
			goalStoreService.deleteGoal(g.getId());
		}
		for (GoalLog gl : getGoalLogs(user)) {
			goalLogStoreService.deleteGoalLog(gl.getId());
		}
		for (Follow fee : getFollowees(user)) {
			followService.deleteFollow(fee.getId());
		}
		for (Follow fer :getFollowers(user)) {
			followService.deleteFollow(fer.getId());
		}
	}
}
