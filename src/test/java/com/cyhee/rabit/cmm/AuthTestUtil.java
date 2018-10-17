package com.cyhee.rabit.cmm;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthTestUtil {
	
	public static void setAdmin() {
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(new TestAuthentication());		
	}
	
	private static class TestAuthentication implements Authentication {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getName() {
			return null;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		@Override
		public Object getCredentials() {
			return null;
		}

		@Override
		public Object getDetails() {
			return null;
		}

		@Override
		public Object getPrincipal() {
			return null;
		}

		@Override
		public boolean isAuthenticated() {
			return true;
		}

		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {			
		}
		
	}

}
