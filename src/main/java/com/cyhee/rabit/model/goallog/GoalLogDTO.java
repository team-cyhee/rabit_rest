package com.cyhee.rabit.model.goallog;

import lombok.Data;

public class GoalLogDTO {
	
	@Data
	public static class PostOneFile {
		private String content;
		private Long fileId;		
	}
}
