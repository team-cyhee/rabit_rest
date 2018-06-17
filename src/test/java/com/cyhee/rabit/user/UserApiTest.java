package com.cyhee.rabit.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cyhee.rabit.cmm.ApiTestUtil;
import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;
import com.cyhee.rabit.user.service.UserService;
import com.cyhee.rabit.user.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=UserController.class, secure=false)
public class UserApiTest {
	@Autowired
	private MockMvc mvc;
	@MockBean(name="basicUserService")
    private UserService userService;
	private ObjectMapper mapper;
	private MediaType contentType;
	
	private static final String url = "/rest/v1/users";
	
	private String getUrl() {
		return url;
	}
	
	private String getUrl(long id) {
		return url+"/"+String.valueOf(id);
	}
	
	@Before
	public void setup() {
		mapper = new ObjectMapper();
		contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8")
		);		
	}
	
	@Test
	public void CRUD() throws Exception {
		User user = new User("email@rabit.com","password@$12","user1","name1","010-1234-1234", null);
		
		given(userService.getUser(1L)).willReturn(user);
		given(userService.getUser(2L)).willThrow(NoSuchUserException.class);
		Mockito.doThrow(NoSuchUserException.class)
			.when(userService)
			.deleteUser(2L);
		Mockito.doThrow(NoSuchUserException.class)
			.when(userService)
			.updateUser(eq(2L), any());
		
		ApiTestUtil testUtil = new ApiTestUtil(mvc, url);
		testUtil.simpleCRUDTest(1L, user);
		testUtil.getDetailTest(1L, "email", "email@rabit.com");
		
		mvc.perform(get(getUrl(2L)))
			.andExpect(status().isNotFound());		
		
		mvc.perform(delete(getUrl(2L)))
			.andExpect(status().isNotFound());
		
		mvc.perform(put(getUrl(2L)).contentType(contentType).content(mapper.writeValueAsString(user)))
			.andExpect(status().isNotFound());
		
	}
}
