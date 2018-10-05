package com.cyhee.rabit.web.user;

import javax.annotation.Resource;

import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserJsonView;
import com.cyhee.rabit.validation.SetPasswordGroup;
import com.cyhee.rabit.validation.exception.ValidationFailException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("rest/v1/users")
public class UserController {
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "userStoreService")
	private UserStoreService userStoreService;
	
	@GetMapping
	public ResponseEntity<Page<User>> getUsers(@PageableDefault Pageable pageable) {
		return new ResponseEntity<Page<User>>(userService.getUsers(pageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getUser(@PathVariable long id) {		
		User user = userService.getUser(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody User userForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ValidationFailException(bindingResult.getAllErrors());
		
		userService.updateUser(id, userForm);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable long id, @PageableDefault Pageable pageable) {
		userStoreService.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}
