package com.cyhee.rabit.model.goal;

import lombok.Data;

import java.util.Date;

public class GoalDTO {
	
	@Data
	public static class PostOneFile {
		private String content;
		private Date startDate;
		private Date endDate;
		private GoalUnit doUnit;
		private Integer doTimes;
		private Long fileId;
	}
}
