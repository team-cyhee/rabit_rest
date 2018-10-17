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
			throw new UnAuthorizedException("Not authorized user");
		return authentication.getPrincipal().toString();
	}
	
	/**
	 * content의 작성자인지 확인
	 * @throws UnAuthorizedException
	 */
	public static void isAuthorOrAdmin(Object content) {
		User author = ContentHelper.getOwner(content);
		String username = AuthHelper.getUsername();
		
		if(!author.getUsername().equals(username))
			throw new UnAuthorizedException("User is not a owner of the content");
	}
}
