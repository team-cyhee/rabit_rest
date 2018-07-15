package com.cyhee.rabit.auth.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cyhee.rabit.user.dao.UserRepository;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;

@Service("basicAuthService")
public class BasicAuthService implements AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private final String url = "http://localhost:8081/oauth/token";
	private final String clientId = "rabit_rest";
	private final String clientSecret = "rabit@password";
	
	public String getToken(String username, String password) throws RestClientException {		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", getAuthorizationHeader());
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "password");
		body.add("username", username);
		body.add("password", password);
		
		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		
		ParameterizedTypeReference<HashMap<String, String>> responseType =  new ParameterizedTypeReference<HashMap<String, String>>() {};
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<HashMap<String, String>> response = restTemplate.exchange(url, HttpMethod.POST, request, responseType);
		
		return response.getBody().get("access_token");
	}
	
	public boolean check(String username, String password) {
		Optional<User> userOpt = userRepository.findByUsername(username);
		if(!userOpt.isPresent()) 
			throw new NoSuchUserException();
		
		User user = userOpt.get();
		
		if(!passwordEncoder.matches(password, user.getPassword()))
			return false;
		
		return true;		
	}
	
	private String getAuthorizationHeader() {
		String auth = clientId + ":" + clientSecret;
		String base64Auth = Base64Utils.encodeToString(auth.getBytes());
		return "Basic " + base64Auth;
	}
}
