package com.cyhee.rabit.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.user.UserRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;
import com.cyhee.rabit.service.cmm.AuthHelper;

@Service("userService")
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public Page<User> getUsersByStatusIn(List<UserStatus> statusList, Pageable pageable) {
		return userRepository.findAllByStatusIn(statusList, pageable);
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
		User user = getUser(id);
		
		AuthHelper.isAuthorOrAdmin(user);
		
		setUserProps(user, userForm);
		userRepository.save(user);
	}
	
	public User deleteUser(Long id) {
        User user = getUser(id);
		
		AuthHelper.isAuthorOrAdmin(user);
		
		delete(user);
        return user;
	}
	
	void delete(User user) {
		user.setStatus(UserStatus.DELETED);
		userRepository.save(user);
	}
	
	private void setUserProps(User user, User userForm) {
		user.setBirth(userForm.getBirth());
		user.setName(userForm.getName());
		user.setPhone(userForm.getPhone());
		user.setStatus(userForm.getStatus());
	}
}
