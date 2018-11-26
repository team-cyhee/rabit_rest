package com.cyhee.rabit.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.cmm.AuthTestUtil;
import com.cyhee.rabit.dao.report.ReportRepository;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.report.Report;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.alarm.AlarmService;
import com.cyhee.rabit.service.cmm.ContentService;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.cs.QuestionService;
import com.cyhee.rabit.service.file.FileService;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.like.LikeService;
import com.cyhee.rabit.service.notice.NoticeService;
import com.cyhee.rabit.service.report.ReportService;
import com.cyhee.rabit.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({ReportService.class, UserService.class, GoalService.class, GoalLogService.class, 
	CommentService.class, QuestionService.class, FileService.class, FollowService.class, 
	LikeService.class, NoticeService.class, AlarmService.class, ReportService.class, 
	ContentService.class})
public class ReportServiceTest {
	@Autowired
	private TestEntityManager entityManger;	
	@Autowired
	private ReportService reportService;
	@Autowired
	private ReportRepository reportRepository;
	
	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	Goal goal;
	GoalLog goalLog;
	Report report1;
	Report report2;
	Report report3;
	Report report4;
	Report report5;
	Report report6;
	
	@Before
	public void setup() {
		AuthTestUtil.setAdmin();
		
		user1 = new User();
		user1.setUsername("user1");
		user1.setEmail("user1@a.c");
		user2 = new User();
		user2.setUsername("user2");
		user2.setEmail("user2@a.c");
		user3 = new User();
		user3.setUsername("user3");
		user3.setEmail("user3@a.c");
		user4 = new User();
		user4.setUsername("user4");
		user4.setEmail("user4@a.c");
		user5 = new User();
		user5.setUsername("user5");
		user5.setEmail("user5@a.c");

		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(user3);
		entityManger.persist(user4);
		entityManger.persist(user5);
		
		goal = new Goal();
		goal.setAuthor(user1);
		goalLog = new GoalLog();
		goalLog.setGoal(goal);
		
		entityManger.persist(goal);
		entityManger.persist(goalLog);
		
		report1 = generate(ContentType.GOAL, goal.getId(), user1);
		report2 = generate(ContentType.GOAL, goal.getId(), user2);
		report3 = generate(ContentType.GOAL, goal.getId(), user3);
		report4 = generate(ContentType.GOAL, goal.getId(), user4);
		report5 = generate(ContentType.GOAL, goal.getId(), user5);
		report6 = generate(ContentType.GOALLOG, goalLog.getId(), user5);
	}
	
	@Test
	public void createAndGet() {
		reportService.addReport(report1);
		
		assertThat(reportService.getReport(report1.getId()))
			.isEqualTo(report1);
	}
	
	@Test
	public void countAndExists() {
		// target is GOAL
		reportService.addReport(report1);
		reportService.addReport(report2);
		reportService.addReport(report3);
		// target is GOALLOG
		reportService.addReport(report6);
		
		assertThat(reportRepository.count(ContentType.GOAL, goal.getId()))
			.isEqualTo(3);
	}
	
	@Test
	public void reportedContent() {
		reportService.addReport(report1);
		reportService.addReport(report2);
		reportService.addReport(report3);
		reportService.addReport(report4);
		reportService.addReport(report5);
		
		assertThat(goal.getStatus())
			.isEqualTo(ContentStatus.REPORTED);
	}
	
	@Test
	public void alreadyReported() {
		reportService.addReport(report1);
		assertThatThrownBy(() -> reportService.addReport(generate(ContentType.GOAL, goal.getId(), user1)))
			.isInstanceOf(DataIntegrityViolationException.class)
			.hasMessage("Already reported!");
	}
	
	public static Report generate(ContentType type, Long id, User reporter) {
		Report report = new Report();
		report.setTargetType(type);
		report.setTargetId(id);
		report.setReporter(reporter);
		return report;
	}
}
