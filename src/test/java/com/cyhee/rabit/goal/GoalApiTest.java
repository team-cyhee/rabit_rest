package com.cyhee.rabit.goal;

import static org.mockito.BDDMockito.given;

import com.cyhee.rabit.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cyhee.rabit.cmm.ApiTestUtil;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.web.goal.GoalController;

	@RunWith(SpringRunner.class)
	@WebMvcTest(controllers=GoalController.class, secure=false)
	@EnableSpringDataWebSupport
	public class GoalApiTest {
		@Autowired
		private MockMvc mvc;
		@MockBean(name="userService")
		private UserService userService;
		@MockBean(name="goalService")
		private GoalService goalService;
		@MockBean(name="goalStoreService")
		private GoalStoreService goalStoreService;

		private static final String url = "/rest/v1/goals";
	
	@Test
	public void CRUD() throws Exception {
		Goal goal = new Goal().setContent("content");
		given(goalService.getGoal(1L)).willReturn(goal);
		
		ApiTestUtil testUtil = new ApiTestUtil(mvc, url);
		testUtil.simpleCRUDTest(1L, goal);
		testUtil.getDetailTest(1L, "content", "content");
	}
}
