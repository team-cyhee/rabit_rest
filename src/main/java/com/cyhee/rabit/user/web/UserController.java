package com.cyhee.rabit.user.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.user.dao.UserRepository;
import com.cyhee.rabit.user.exception.MalformedUserException;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.exception.UserException;
import com.cyhee.rabit.user.model.User;
import com.cyhee.rabit.web.model.ApiErrorCode;
import com.cyhee.rabit.web.model.ApiResponseEntity;

@RestController
@RequestMapping("rest/v1/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

    @RequestMapping(method=RequestMethod.GET)
    public ApiResponseEntity<Iterable<User>> getAllUsers() {
        return new ApiResponseEntity<Iterable<User>>(userRepository.findAll(), HttpStatus.BAD_GATEWAY);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ApiResponseEntity<Void> addUser(@RequestBody User user) {    	
        try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new MalformedUserException();
		}
        return new ApiResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ApiResponseEntity<User> getUser(@PathVariable int id) {
    	Optional<User> userOpt = userRepository.findById(id);
    	if(userOpt.isPresent())
    		return new ApiResponseEntity<User>(userOpt.get(), HttpStatus.OK);
    	throw new NoSuchUserException();
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ApiResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User user) {
    	Optional<User> userOpt = userRepository.findById(id);
    	if(!userOpt.isPresent())
    		throw new NoSuchUserException();
    	
    	user.setId(id);
        try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new MalformedUserException();
		}
        return new ApiResponseEntity<>(HttpStatus.CREATED); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ApiResponseEntity<Void> deleteUser(@PathVariable int id) {
    	Optional<User> userOpt = userRepository.findById(id);
    	if(!userOpt.isPresent())
    		throw new NoSuchUserException();
    	
        userRepository.deleteById(id);
        return new ApiResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
    
    @ExceptionHandler(value = UserException.class)
    public ApiResponseEntity<Void> userExceptionHandler(UserException e) {
    	return new ApiResponseEntity<>(e.getApiErrorCode(), e, e.getStatus());
    }
}
