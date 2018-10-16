
package com.cyhee.rabit.model.goallog;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.main.MainInfo;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalLogInfo extends MainInfo {
	private Long id;
	private User author;
	private Goal goal;
	private String content;
	private List<FileInfo> file;

	public GoalLogInfo(GoalLog from, Integer likeNum, Integer commentNum, Page<Comment> comments) {
		super(likeNum, commentNum, comments, from.getLastUpdated());
		this.goal = from.getGoal();
		this.content = from.getContent();
		this.file = from.getFile();
	}
}
