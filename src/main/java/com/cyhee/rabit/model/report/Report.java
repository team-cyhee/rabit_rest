package com.cyhee.rabit.model.report;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class Report extends TimestampEntity {

	@ManyToOne(optional=false)
	@JoinColumn(name="reporter_id")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User reporter;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private ReportType type;
	
//	@Column(columnDefinition="TEXT")
//	private String content;

	@Column
	@Enumerated(EnumType.STRING)
	private ContentType targetType;

	@Column
	private Long targetId;
	
	@Column(nullable=false)
	private ContentStatus status = ContentStatus.ACTIVE;
}
