package com.cyhee.rabit.model.notice;

import javax.persistence.*;
import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.cmm.ContentStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class Notice extends TimestampEntity {

	@Column(columnDefinition = "TEXT")
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(nullable=false)
	private ContentStatus status = ContentStatus.ACTIVE;
}
