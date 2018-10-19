
package com.cyhee.rabit.model.goallog;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.main.MainInfo;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalLogInfo extends MainInfo {
	private Long id;
	private User author;
	private Goal goal;
	private String content;
	private Date createDate;
	private List<FileInfo> file;

	public GoalLogInfo(GoalLog from, Integer likeNum, Integer commentNum, Integer companionNum, Page<Comment> comments) {
		super(ContentType.GOALLOG, likeNum, commentNum, companionNum, comments, from.getLastUpdated());
		this.id = from.getId();
		this.author = from.getGoal().getAuthor();
		this.goal = from.getGoal();
		this.content = from.getContent();
		this.createDate = from.getCreateDate();
		this.file = from.getFile();
	}
}
