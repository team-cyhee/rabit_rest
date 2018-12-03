
package com.cyhee.rabit.model.page;

import java.util.Date;
import java.util.List;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.GoalUnit;
import com.cyhee.rabit.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private GoalUnit doUnit;
	private Integer doTimes;
	private Integer logNum;
	private List<FileInfo> files;
	private Double achievementRate;

	public GoalInfo(Goal from, Integer logNum, Integer likeNum, Integer commentNum, Integer companionNum, boolean liked, Double achievementRate) {
		super(null, ContentType.GOAL, likeNum, commentNum, companionNum, from.getCreateDate(), from.getLastUpdated(), liked);
		this.id = from.getId();
		this.author = from.getAuthor();
		this.parent = from.getParent();
		this.content = from.getContent();
		this.startDate = from.getStartDate();
		this.endDate = from.getEndDate();
		this.doUnit = from.getDoUnit();
		this.doTimes = from.getDoTimes();
		this.logNum = logNum;
		this.files = from.getFiles();
		this.achievementRate = achievementRate;
	}
}
