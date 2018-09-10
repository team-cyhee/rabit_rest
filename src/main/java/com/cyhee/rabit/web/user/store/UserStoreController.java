package com.cyhee.rabit.web.user.store;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.store.UserStoreService;

@RestController
@RequestMapping("rest/v1/users/{id}")
public class UserStoreController {
	@Autowired
	UserStoreService userStoreService;
	
	@Resource(name="basicUserService")
	UserService userService;
	
	@GetMapping("/goals")
	public ResponseEntity<Page<Goal>> getGoals(@PathVariable long id, Pageable pageable) {
		User author = userService.getUser(id);
		return new ResponseEntity<>(userStoreService.getGoals(author, pageable), HttpStatus.OK);
	}
}
