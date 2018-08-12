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
	
	public void addUser(User user) throws DuplicateUserException{
		/*Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
    	if(userOpt.isPresent())
    		throw new DuplicateUserException();*/    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateUserException();
		}
	}
	
	public User getUser(Long id) throws NoSuchUserException {
		return findUser(id);
	}
	
	public User getUserByUsername(String username) throws NoSuchUserException {
		return findUser(username);
	}

	public User getUserByEmail(String email) throws NoSuchUserException {
		Optional<User> userOpt = userRepository.findByEmail(email);
    	if(!userOpt.isPresent())
    		throw new NoSuchUserException(email);    	
    	return userOpt.get();
	}
	
	public void updateUser(Long id, User userForm) throws NoSuchUserException {    	
    	updateUser(findUser(id), userForm);
	}
	
	public void updateUserByUsername(String username, User userForm) throws NoSuchUserException {
		updateUser(findUser(username), userForm);
	}
	
	public void deleteUser(Long id) throws NoSuchUserException {
        userRepository.delete(findUser(id));
	}
	
	public void deleteUserByUsername(String username) throws NoSuchUserException {    	
        userRepository.delete(findUser(username));
	}
	
	private User findUser(Long id) throws NoSuchUserException {
		Optional<User> userOpt = userRepository.findById(id);
    	if(!userOpt.isPresent())
    		throw new NoSuchUserException(id);    	
    	return userOpt.get();
	}
	
	private User findUser(String username) throws NoSuchUserException {
		Optional<User> userOpt = userRepository.findByUsername(username);
    	if(!userOpt.isPresent())
    		throw new NoSuchUserException(username);    	
    	return userOpt.get();
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
