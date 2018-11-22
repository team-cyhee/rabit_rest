package com.cyhee.rabit.service.cmm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.cyhee.rabit.exception.cmm.UnsupportedContentException;
import com.cyhee.rabit.model.cmm.ContentType;

public class ResponseHelper {

	public static ResponseEntity<Void> createdEntity(ContentType type, Object id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, createdLocation(getCurrentRequest(), type, id));
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	public static ResponseEntity<Void> createdEntity(HttpServletRequest request, ContentType type, Object id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, createdLocation(request, type, id));
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	private static String createdLocation(HttpServletRequest request, ContentType type, Object id) {
		return UriComponentsBuilder.fromUriString(baseUrl(request)).path(resourcePath(type, id)).build().toUriString();
	}

	private static String baseUrl(HttpServletRequest request) {
		return String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
	}

	private static String resourcePath(ContentType type, Object id) {
		String prefix = "/rest/v1";
		String resource = null;
		String key = "/" + id;

		switch (type) {
		case NOTICE:
			resource = "/notices";
			break;
		case QUESTION:
			resource = "/questions";
			break;
		case COMMENT:
			resource = "/comments";
			break;
		case FILE:
			resource = "/files";
			break;
		case FOLLOW:
			resource = "/follows";
			break;
		case GOAL:
			resource = "/goals";
			break;
		case GOALLOG:
			resource = "/goallogs";
			break;
		case LIKE:
			resource = "/likes";
			break;
		case USER:
			resource = "/users";
			break;
		default:
			throw new UnsupportedContentException();
		}
		return prefix + resource + key;
	}

	private static HttpServletRequest getCurrentRequest() {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		Assert.state(attrs instanceof ServletRequestAttributes, "No current ServletRequestAttributes");
		return ((ServletRequestAttributes) attrs).getRequest();
	}
}
