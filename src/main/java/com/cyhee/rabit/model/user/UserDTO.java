package com.cyhee.rabit.model.user;

import lombok.Data;

import java.util.Date;

public class UserDTO {
	
	@Data
	public static class PostOneFile {
		private String name;
		private Date birth;
		private String introduction;
		private Long fileId;
	}
}
