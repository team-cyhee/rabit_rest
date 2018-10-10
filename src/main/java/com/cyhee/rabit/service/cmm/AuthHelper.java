package com.cyhee.rabit.service.cmm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.web.cmm.exception.UnAuthorizedException;

public class AuthHelper {

	/**
	 * 인증된 경우 token의 username
	 * 인증되지 않았을 경우 null
	 */
	public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null)
			return null;
		return authentication.getPrincipal().toString();
	}
	
	public static void isAuthor(User author) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || author.getUsername().equals(authentication.getPrincipal()))
			throw new UnAuthorizedException();
	}
}
