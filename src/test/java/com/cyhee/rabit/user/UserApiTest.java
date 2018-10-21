package com.cyhee.rabit.user;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cyhee.rabit.cmm.ApiTestUtil;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;
import com.cyhee.rabit.web.user.UserController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=UserController.class, secure=false)
@EnableSpringDataWebSupport
public class UserApiTest {
	@Autowired
	private MockMvc mvc;
	@MockBean(name="userService")
    private UserService userService;
	@MockBean(name="userStoreService")
    private UserStoreService userStoreService;
	private ApiTestUtil apiTester;
	
	private static final String url = "/rest/v1/users";
	
	/*private String getUrl() {
		return url;
	}*/
	
	private String getUrl(long id) {
		return url+"/"+String.valueOf(id);
	}
	
	@Before
	public void setup() {
		apiTester = new ApiTestUtil(mvc, url);
	}
	
	@Test
	public void CRUD() throws Exception {
		User user = new User().setEmail("user@email.com").setUsername("username");
		
		given(userService.getUser(1L)).willReturn(user);
		given(userService.getUser(2L)).willThrow(NoSuchContentException.class);
		Mockito.doThrow(NoSuchContentException.class)
			.when(userStoreService)
			.deleteUser(2L);
		Mockito.doThrow(NoSuchContentException.class)
			.when(userService)
			.updateUser(eq(2L), any());

		apiTester.simpleRUDTest(1L, user);

		mvc.perform(get(getUrl(1L)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email", is(user.getEmail())));
		
		mvc.perform(get(getUrl(2L)))
			.andExpect(status().isNotFound());		
		
		mvc.perform(delete(getUrl(2L)))
			.andExpect(status().isNotFound());
		
		mvc.perform(put(getUrl(2L)).contentType(apiTester.getMediaType())
			.content(apiTester.getMapper().writeValueAsString(user)))
			.andExpect(status().isNotFound());
		
	}
}
