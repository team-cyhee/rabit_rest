
package com.cyhee.rabit.model.goal.factory;

import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.GoalCycle;
import com.cyhee.rabit.model.main.MainInfo;
import com.cyhee.rabit.model.user.User;

import java.util.Date;
import java.util.List;

public class GoalInfo extends MainInfo {
	private Goal parent;
	private String content;
	private Date startDate;
	private Date endDate;
	private GoalCycle selectedDays;
	private List<FileInfo> files;
}
