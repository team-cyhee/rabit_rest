package com.cyhee.rabit.service.user;

import com.cyhee.rabit.dao.follow.FollowRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.follow.FollowStatus;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goallog.GoalLogService;
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
	private GoalService goalService;

	@Autowired
	private GoalLogService goalLogService;

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

	public Page<Follow> getFollowers(User followee, Pageable pageable) {
		return followRepository.findByFolloweeAndStatusNot(followee, FollowStatus.INACTIVE, pageable);
	}

	public List<Follow> getFollowers(User followee) {
		return followRepository.findByFolloweeAndStatusNot(followee, FollowStatus.INACTIVE);
	}

	public Page<Follow> getFollowees(User follower, Pageable pageable) {
		return followRepository.findByFollowerAndStatusNot(follower, FollowStatus.INACTIVE, pageable);
	}

	public List<Follow> getFollowees(User follower) {
		return followRepository.findByFollowerAndStatusNot(follower, FollowStatus.INACTIVE);
	}

	public void deleteAllUserStore(User user) {
		getGoals(user).forEach(g -> goalService.deleteGoal(g.getId()));
		getGoalLogs(user).forEach(gl -> goalLogService.deleteGoalLog(gl.getId()));
		getFollowees(user).forEach(fee -> followService.deleteFollow(fee.getId()));
		getFollowers(user).forEach(fer -> followService.deleteFollow(fer.getId()));
	}
}
