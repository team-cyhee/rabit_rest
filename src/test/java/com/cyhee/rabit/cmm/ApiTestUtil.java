package com.cyhee.rabit.cmm;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiTestUtil {
	private ObjectMapper mapper; 
	private MediaType mediaType;
	private MockMvc mvc;
	private String url;
	
	public ApiTestUtil(MockMvc mvc, String url) {
		this.mvc = mvc;
		this.url = url;
		this.mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		this.mediaType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8")
		);
	}
	
	public ApiTestUtil(MockMvc mvc, String url, ObjectMapper mapper, MediaType mediaType) {
		this.mvc = mvc;
		this.url = url;
		this.mapper = mapper;
		this.mediaType = mediaType;
	}

	private  String getUrl(Object id) {
		return url+"/"+String.valueOf(id);
	}
	
	/**
	 * base url에대하여 post, get, put, delete 요청을 수행한다
	 * @param id - 'url/id'에 대하여 get, put, delete 요청이 수행
	 * @param target - put, post에 사용될 request body
	 */
	public void simpleCRUDTest(Object id, Object target) throws JsonProcessingException, Exception {
		mvc.perform(post(url).contentType(mediaType).content(mapper.writeValueAsString(target)))
			.andExpect(status().isCreated());

		mvc.perform(get(url).param("page", "0").param("size", "10"))
	        .andExpect(status().isOk());
		
		mvc.perform(get(getUrl(id)))
			.andExpect(status().isOk());
		
		mvc.perform(put(getUrl(id)).contentType(mediaType).content(mapper.writeValueAsString(target)))
			.andExpect(status().isOk());
		
		mvc.perform(delete(getUrl(id)))
			.andExpect(status().isAccepted());
	}
	
	/**
	 * base url에대하여 get, put, delete 요청을 수행한다
	 * @param id - 'url/id'에 대하여 get, put, delete 요청이 수행
	 * @param target - put에 사용될 request body
	 */
	public void simpleRUDTest(Object id, Object target) throws JsonProcessingException, Exception {
		mvc.perform(get(url).param("page", "0").param("size", "10"))
	        .andExpect(status().isOk());
		
		mvc.perform(get(getUrl(id)))
			.andExpect(status().isOk());
		
		mvc.perform(put(getUrl(id)).contentType(mediaType).content(mapper.writeValueAsString(target)))
			.andExpect(status().isOk());
		
		mvc.perform(delete(getUrl(id)))
			.andExpect(status().isAccepted());
	}
	
	/**
	 * url에 대하여 단순 get 요청을 했을 때 인자를 검증
	 * @param id - 'url/id' endpoint
	 * @param type - 검증할 인자의 json parameter
	 * @param target - 검증할 인자와 비교할 값
	 */
	public void getDetailTest(Object id, String type, Object target) throws Exception {
		mvc.perform(get(getUrl(1L)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$."+type, is(target)));
	}
}
