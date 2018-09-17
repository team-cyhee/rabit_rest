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

@Service
public class UserStoreService {
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

	public Page<Goal> getGoals(User author, Pageable pageable) {
		return goalRepository.findAllByAuthor(author, pageable);
	}
	
	public Page<GoalLog> getGoalLogs(User author, Pageable pageable) {
		return goalLogRepository.findAllByAuthor(author, pageable);
	}

	public Page<Follow> getFollowers(User followee, Pageable pageable) {
		return followRepository.findByFollowee(followee, pageable);
	}

	public Page<Follow> getFollowees(User follower, Pageable pageable) {
		return followRepository.findByFollower(follower, pageable);
	}

	public void deleteAllUserStore(User user, Pageable pageable) {
		getGoals(user, pageable).forEach(g -> goalService.deleteGoal(g.getId(), pageable));
		getGoalLogs(user, pageable).forEach(gl -> goalLogService.deleteGoalLog(gl.getId()));
		getFollowees(user, pageable).forEach(fee -> followService.deleteFollow(fee.getId()));
		getFollowers(user, pageable).forEach(fer -> followService.deleteFollow(fer.getId()));
	}
}
