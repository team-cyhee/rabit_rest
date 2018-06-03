package com.cyhee.rabit.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.user.dao.UserRepository;
import com.cyhee.rabit.user.exception.DuplicateUserException;
import com.cyhee.rabit.user.exception.MalformedUserException;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;

@Service("basicUserService")
public class BasicUserService implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public void addUser(User user) throws MalformedUserException{
		Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
    	if(userOpt.isPresent())
    		throw new DuplicateUserException();
    	
    	user.setPassword(passwordEncoder.encode((user.getPassword())));
		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new MalformedUserException();
		}
	}
	
	public User getUser(Long id) throws NoSuchUserException {
		Optional<User> userOpt = userRepository.findById(id);
    	if(userOpt.isPresent())
    		return userOpt.get();
    	throw new NoSuchUserException();
	}
	
	public void updateUser(Long id, User userForm) throws NoSuchUserException {
		Optional<User> userOpt = userRepository.findById(id);
    	if(!userOpt.isPresent())
    		throw new NoSuchUserException();
    	
    	User user = userOpt.get();
    	user.setBirth(userForm.getBirth());
    	user.setName(userForm.getName());
    	user.setPassword(passwordEncoder.encode((userForm.getPassword())));
    	user.setPhone(userForm.getPhone());
    	user.setStatus(userForm.getStatus());
    	
        try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new MalformedUserException();
		} 
	}
	
	public void deleteUser(Long id) throws NoSuchUserException {
		Optional<User> userOpt = userRepository.findById(id);
    	if(!userOpt.isPresent())
    		throw new NoSuchUserException();
    	
        userRepository.deleteById(id);
	}
}
