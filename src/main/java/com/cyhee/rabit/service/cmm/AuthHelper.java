package com.cyhee.rabit.service.cmm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cyhee.rabit.exception.cmm.UnauthorizedException;
import com.cyhee.rabit.model.user.User;

public class AuthHelper {

	/**
	 * 인증된 경우 token의 username
	 * 인증되지 않았을 경우 null
	 * @throws UnauthorizedException
	 */
	public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication.getPrincipal() == null || authentication.getPrincipal().equals("anonymousUser"))
			throw new UnauthorizedException("Not authorized user");
		return authentication.getPrincipal().toString();
	}
	
	/**
	 * content의 작성자인지 확인
	 * @throws UnauthorizedException
	 */
	public static void isAuthorOrAdmin(Object content) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null)
			throw new UnauthorizedException("Not authenticated");		
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
			return;
		
		User author = ContentHelper.getOwner(content);
		String username = AuthHelper.getUsername();
		
		if(!author.getUsername().equals(username))
			throw new UnauthorizedException("User is not an owner of the content");
	}
}
