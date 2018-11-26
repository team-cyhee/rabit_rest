package com.cyhee.rabit.service.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.exception.cmm.UnsupportedContentException;
import com.cyhee.rabit.model.cmm.Content;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.service.alarm.AlarmService;
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

@Service
public class ContentService {
	@Autowired UserService userService;	
	@Autowired GoalService goalService;	
	@Autowired GoalLogService goalLogService;	
	@Autowired CommentService commentService;
	@Autowired QuestionService questionService;
	@Autowired FileService fileService;
	@Autowired FollowService followService;
	@Autowired LikeService likeService;
	@Autowired NoticeService noticeService;
	@Autowired AlarmService alarmService;
	@Autowired ReportService reportService;
	
	private Object getObject(ContentType type, Long id) {
		return get(type, id);
	}
	
	public Content getContent(ContentType type, Long id) {
		Object obj = get(type, id);
		if(obj instanceof Content)
			return (Content) obj;
		throw new UnsupportedContentException("This is not instance of Content");
	}
	
	private Object get(ContentType type, Long id) {
		switch(type) {
			case ALARM: return alarmService.getAlarm(id);
			case COMMENT: return commentService.getComment(id);
			case FILE: return fileService.getFile(id);
			case FOLLOW: return followService.getFollow(id);
			case GOAL: return goalService.getGoal(id);
			case GOALLOG: return goalLogService.getGoalLog(id);
			case LIKE: return likeService.getLike(id);
			case NOTICE: return noticeService.getNotice(id);
			case QUESTION: return questionService.getQuestion(id);
			case REPORT: return reportService.getReport(id);
			case USER: return userService.getUser(id);
			default: throw new UnsupportedContentException();
		}
	}
}
