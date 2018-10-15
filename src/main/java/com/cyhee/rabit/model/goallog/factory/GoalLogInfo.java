
package com.cyhee.rabit.model.goallog.factory;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.main.MainInfo;

import com.cyhee.rabit.model.goal.Goal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class GoalLogInfo extends MainInfo {

	private Goal goal;
	private String content;
	private List<FileInfo> file;
	private Integer likeNum;
	private Integer commentNum;
	private Page<Comment> comments;

	public GoalLogInfo(GoalLog from, Integer likeNum, Integer commentNum, Page<Comment> comments) {
		this.goal = from.getGoal();
		this.content = from.getContent();
		this.file = from.getFile();
		this.likeNum = likeNum;
		this.commentNum = commentNum;
		this.comments = comments;
	}
}
