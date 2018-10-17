package com.cyhee.rabit.service.cmm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cyhee.rabit.exception.cmm.UnAuthorizedException;
import com.cyhee.rabit.model.user.User;

public class AuthHelper {

	/**
	 * 인증된 경우 token의 username
	 * 인증되지 않았을 경우 null
	 * @throws UnAuthorizedException
	 */
	public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication.getPrincipal() == null || authentication.getPrincipal().equals("anonymousUser"))
			throw new UnAuthorizedException();
		return authentication.getPrincipal().toString();
	}
	
	public static void isAuthor(User author) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || author.getUsername().equals(authentication.getPrincipal()))
			throw new UnAuthorizedException();
	}
}
