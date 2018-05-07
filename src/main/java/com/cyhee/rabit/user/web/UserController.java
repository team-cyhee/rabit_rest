package com.cyhee.rabit.user.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.user.exception.UserException;
import com.cyhee.rabit.user.model.User;
import com.cyhee.rabit.user.service.UserService;
import com.cyhee.rabit.web.model.ApiResponseEntity;

@RestController
@RequestMapping("rest/v1/users")
public class UserController {
	@Resource(name="basicUserService")
	private UserService userService;

    @RequestMapping(method=RequestMethod.GET)
    public ApiResponseEntity<Iterable<User>> getAllUsers() {
        return new ApiResponseEntity<Iterable<User>>(userService.getAllUsers(), HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ApiResponseEntity<Void> addUser(@RequestBody User user) {    	
    	userService.addUser(user);
        return new ApiResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ApiResponseEntity<User> getUser(@PathVariable int id) {
    	return new ApiResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ApiResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User userForm) {
    	userService.updateUser(id, userForm);
        return new ApiResponseEntity<>(HttpStatus.CREATED); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ApiResponseEntity<Void> deleteUser(@PathVariable int id) {
    	userService.deleteUser(id);
        return new ApiResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
    
    @ExceptionHandler(value = UserException.class)
    public ApiResponseEntity<Void> userExceptionHandler(UserException e) {
    	return new ApiResponseEntity<>(e.getApiErrorCode(), e, e.getStatus());
    }
}
