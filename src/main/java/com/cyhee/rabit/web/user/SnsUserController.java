package com.cyhee.rabit.web.user;

import javax.annotation.Resource;

import com.cyhee.rabit.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cyhee.rabit.web.cmm.model.ApiError;
import com.cyhee.rabit.web.cmm.model.ApiErrorCode;
import com.cyhee.rabit.model.user.SnsUser;
import com.cyhee.rabit.model.user.User;

// TODO It should be moved to authorization server
@RestController
@RequestMapping("rest/v1/sns/users")
public class SnsUserController {
	/*private static Logger logger = LogManager.getLogger(SnsUserController.class);
	
	@Resource(name="userService")
	UserService userService;
	
	String authServer = "http://localhost:8081/oauth/naver/profile";
	
	@PostMapping
	public ResponseEntity<?> addUserByNaver(@RequestBody SnsUser form) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + form.getToken());
		
		try {
			ResponseEntity<User> response = restTemplate.exchange(authServer, HttpMethod.GET, new HttpEntity<>(headers), User.class);
			User user = response.getBody();
			
			user.setUsername(form.getUsername());
			if(form.getName() != null)
				user.setName(form.getName());
			if(form.getPhone() != null)
				user.setPhone(form.getPhone());
			if(form.getBirth() != null)
				user.setBirth(form.getBirth());
			
			userService.addUser(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
			
		} catch (RestClientException e) {
			logger.debug(e);
			if(e instanceof HttpStatusCodeException) {
				HttpStatus code = ((HttpStatusCodeException) e).getStatusCode();
				return new ResponseEntity<ApiError>(new ApiError(ApiErrorCode.TOKEN_AUTHORIZATION_FAIL, "Unauthorized token"), code);
			}
			return new ResponseEntity<ApiError>(new ApiError(ApiErrorCode.TOKEN_AUTHORIZATION_FAIL, e), HttpStatus.BAD_GATEWAY);
		}
	}*/
}
