package com.cyhee.rabit.auth.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.auth.service.AuthService;
import com.cyhee.rabit.web.model.ApiResponseEntity;

@RestController
@RequestMapping("rest/v1/auth")
public class AuthController {
	@Resource(name="basicAuthService")
	private AuthService authService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ApiResponseEntity<Void> auth(@RequestParam String username, @RequestParam String password) {    	
    	boolean result = authService.check(username, password);
    	if(result)
    		return new ApiResponseEntity<>(HttpStatus.OK);
    	else
    		return new ApiResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ApiResponseEntity<String> getToken(@RequestParam String username, @RequestParam String password) {    	
    	return new ApiResponseEntity<>(authService.getToken(username, password), HttpStatus.OK);
    }
}
