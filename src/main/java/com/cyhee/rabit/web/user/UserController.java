package com.cyhee.rabit.web.user;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.exception.cmm.ValidationFailException;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserDTO;
import com.cyhee.rabit.model.user.UserStatus;
import com.cyhee.rabit.service.file.FileService;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;

@RestController
@RequestMapping("rest/v1/users")
public class UserController {
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "userStoreService")
	private UserStoreService userStoreService;

	@Resource(name = "fileService")
	private FileService fileService;
	
	@GetMapping
	public ResponseEntity<Page<User>> getUsers(@PageableDefault Pageable pageable) {
		return new ResponseEntity<Page<User>>(userService.getUsers(pageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getUser(@PathVariable long id) {		
		User user = userService.getUser(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/username/{username}")
	public ResponseEntity<User> getUserByName(@PathVariable String username) {
		User user = userService.getUserByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/username/{username}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUserByName(@PathVariable String username, @RequestBody UserDTO.PostOneFile dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ValidationFailException(bindingResult.getAllErrors());

		User user = userService.getUserByUsername(username);
		user.setName(dto.getName());
		user.setBirth(dto.getBirth());
		user.setIntroduction(dto.getIntroduction());

		if (dto.getFileId() != null) user.getFiles().add(fileService.getFile(dto.getFileId()));

		userService.updateUser(user.getId(), user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody User userForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ValidationFailException(bindingResult.getAllErrors());
		
		userService.updateUser(id, userForm);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable long id) {
		userStoreService.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<Page<User>> searchUsers(@RequestParam String keyword, @PageableDefault Pageable pageable) {
		return new ResponseEntity<Page<User>>(userService.search(keyword, Arrays.asList(UserStatus.values()), pageable), HttpStatus.OK);
	}

}
