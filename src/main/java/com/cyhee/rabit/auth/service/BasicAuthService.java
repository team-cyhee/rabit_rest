package com.cyhee.rabit.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.user.dao.UserRepository;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;

@Service("basicAuthService")
public class BasicAuthService implements AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public boolean check(String username, String password) throws NoSuchUserException {
		Optional<User> userOpt = userRepository.findByUsername(username);
		if(!userOpt.isPresent()) 
			throw new NoSuchUserException();
		
		User user = userOpt.get();
		
		if(!passwordEncoder.matches(password, user.getPassword()))
			return false;
		
		return true;
	}
}
