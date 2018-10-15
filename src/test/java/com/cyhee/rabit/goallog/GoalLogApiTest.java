package com.cyhee.rabit.goallog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cyhee.rabit.cmm.ApiTestUtil;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.web.goallog.GoalLogController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=GoalLogController.class, secure=false)
@EnableSpringDataWebSupport
public class GoalLogApiTest {
	@Autowired
	private MockMvc mvc;
	@MockBean(name="goalLogService")
    private GoalLogService goalLogService;
	@MockBean(name="goalLogStoreService")
    private GoalLogStoreService goalLogStoreService;
	
	private static final String url = "/rest/v1/goallogs";
	
	@Test
	public void CRUD() throws Exception {
		GoalLog log = new GoalLog().setContent("content");
		given(goalLogService.getGoalLog(1L)).willReturn(log);
		
		Page<GoalLog> page = new PageImpl<>(Arrays.asList(log));
		when(goalLogService.getGoalLogs(any())).thenReturn(page);
		
		ApiTestUtil testUtil = new ApiTestUtil(mvc, url);
		testUtil.simpleCRUDTest(1L, log);
		testUtil.getDetailTest(1L, "content", log.getContent());
	}	
}
