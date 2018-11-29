package com.cyhee.rabit.model.cmm;

public interface Content {
	Long getId();
	
	ContentStatus getStatus();
	void setStatus(ContentStatus status);
}
