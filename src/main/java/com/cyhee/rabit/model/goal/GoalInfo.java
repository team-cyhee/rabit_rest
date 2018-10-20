
package com.cyhee.rabit.model.goal;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.GoalCycle;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.main.MainInfo;
import com.cyhee.rabit.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalInfo extends MainInfo {
	private Long id;
	private User author;
	private Goal parent;
	private String content;
	private Date startDate;
	private Date endDate;
	private GoalCycle selectedDays;
	private List<FileInfo> files;

	public GoalInfo(Goal from, Integer likeNum, Integer commentNum, Integer companionNum, Page<Comment> comments) {
		super(ContentType.GOAL, likeNum, commentNum, companionNum, comments, from.getLastUpdated());
		this.id = from.getId();
		this.author = from.getAuthor();
		this.parent = from.getParent();
		this.content = from.getContent();
		this.startDate = from.getStartDate();
		this.endDate = from.getEndDate();
		this.selectedDays = from.getSelectedDays();
		this.files = from.getFiles();
	}
}
