package com.cyhee.rabit.web.user;

import javax.annotation.Resource;

import com.cyhee.rabit.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.validation.SetPasswordGroup;
import com.cyhee.rabit.validation.exception.ValidationFailException;

@RestController
@RequestMapping("rest/v1/signup")
@Deprecated
public class SignUpController {
	@Resource(name = "userService")
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Void> signUp(@RequestBody @Validated({SetPasswordGroup.class}) User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ValidationFailException(bindingResult.getAllErrors());
	
		userService.addUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
