package com.cyhee.rabit.service.user;

import java.util.Optional;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.follow.FollowStatus;
import com.cyhee.rabit.model.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.dao.user.UserRepository;
import com.cyhee.rabit.exception.user.MalformedUserException;
import com.cyhee.rabit.model.user.User;

@Service("basicUserService")
public class BasicUserService implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserStoreService userStoreService;
	
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public User getUser(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
    	if(!userOpt.isPresent())
    		throw new NoSuchContentException(ContentType.USER, id);    	
    	return userOpt.get();
	}
	
	public User getUserByUsername(String username) {
		Optional<User> userOpt = userRepository.findByUsernameAndStatusNot(username, UserStatus.DELETED);
    	if(!userOpt.isPresent())
    		throw new NoSuchContentException(ContentType.USER, "username", username);    	
    	return userOpt.get();
	}

	public User getUserByEmail(String email) {
		Optional<User> userOpt = userRepository.findByEmailAndStatusNot(email, UserStatus.DELETED);
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
        User user = getUser(id);
        user.setStatus(UserStatus.DELETED);
		userStoreService.deleteAllUserStore(user);
	}
	
	public void deleteUserByUsername(String username) {
		User user = getUserByUsername(username);
		user.setStatus(UserStatus.DELETED);
		userStoreService.deleteAllUserStore(user);
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
