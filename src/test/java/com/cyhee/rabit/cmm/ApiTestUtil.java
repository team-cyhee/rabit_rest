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

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiTestUtil {
	private ObjectMapper mapper = new ObjectMapper();
	private MediaType contentType = new MediaType(
		MediaType.APPLICATION_JSON.getType(),
		MediaType.APPLICATION_JSON.getSubtype(),
		Charset.forName("utf8")
	);
	private MockMvc mvc;
	private String url;
	
	public ApiTestUtil(MockMvc mvc, String url) {
		this.mvc = mvc;
		this.url = url;
	}

	private  String getUrl(Object id) {
		return url+"/"+String.valueOf(id);
	}
	
	public void simpleCRUDTest(Object id, Object target) throws JsonProcessingException, Exception {
		mvc.perform(post(url).contentType(contentType).content(mapper.writeValueAsString(target)))
			.andExpect(status().isCreated());

		mvc.perform(get(url).param("page", "0").param("size", "10"))
	        .andExpect(status().isOk());
		
		mvc.perform(get(getUrl(id)))
			.andExpect(status().isOk());
		
		mvc.perform(put(getUrl(id)).contentType(contentType).content(mapper.writeValueAsString(target)))
			.andExpect(status().isOk());
		
		mvc.perform(delete(getUrl(id)))
			.andExpect(status().isAccepted());
	}
	
	public void getDetailTest(Object id, String type, Object target) throws Exception {
		mvc.perform(get(getUrl(1L)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$."+type, is(target)));
	}
}
