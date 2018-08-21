package com.cyhee.rabit.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.cmm.web.exception.NoSuchContentException;
import com.cyhee.rabit.user.dao.UserRepository;
import com.cyhee.rabit.user.exception.MalformedUserException;
import com.cyhee.rabit.user.model.User;

@Service("basicUserService")
public class BasicUserService implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Iterable<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable).getContent();
	}
		
	public void addUser(User user) {    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public User getUser(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
    	if(!userOpt.isPresent())
    		throw new NoSuchContentException(ContentType.USER, id);    	
    	return userOpt.get();
	}
	
	public User getUserByUsername(String username) {
		Optional<User> userOpt = userRepository.findByUsername(username);
    	if(!userOpt.isPresent())
    		throw new NoSuchContentException(ContentType.USER, "username", username);    	
    	return userOpt.get();
	}

	public User getUserByEmail(String email) {
		Optional<User> userOpt = userRepository.findByEmail(email);
    	if(!userOpt.isPresent())
    		throw new NoSuchContentException(ContentType.USER, "email", email);    	
    	return userOpt.get();
	}
	
	public void updateUser(Long id, User userForm) {    	
    	updateUser(getUser(id), userForm);
	}
	
	public void updateUserByUsername(String username, User userForm) {
		updateUser(getUserByUsername(username), userForm);
	}
	
	public void deleteUser(Long id) {
        userRepository.delete(getUser(id));
	}
	
	public void deleteUserByUsername(String username) {    	
        userRepository.delete(getUserByUsername(username));
	}
	
	private void updateUser(User user, User userForm) {
		setUserProps(user, userForm);
    	
        try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new MalformedUserException(e.getMessage());
		} 
	}
	
	private void setUserProps(User user, User userForm) {
		user.setBirth(userForm.getBirth());
		user.setName(userForm.getName());
		user.setPhone(userForm.getPhone());
		user.setStatus(userForm.getStatus());
	}
}
