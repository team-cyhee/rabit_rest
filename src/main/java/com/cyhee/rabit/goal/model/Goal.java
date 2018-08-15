package com.cyhee.rabit.goal.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cyhee.rabit.cmm.model.ContentStatus;
import com.cyhee.rabit.cmm.model.TimestampEntity;
import com.cyhee.rabit.goallog.model.GoalLog;
import com.cyhee.rabit.user.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class Goal extends TimestampEntity {
	
	@ManyToOne(optional=false)
	@JoinColumn(name="author_id", foreignKey = @ForeignKey(name = "FK_USER_GOAL"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User author;
	
	@ManyToOne(cascade=CascadeType.REMOVE, optional=true)
	@JoinColumn(name="parent_id")
	private Goal parent;

	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date endDate;
	
	@Column(nullable=false)
	private ContentStatus status = ContentStatus.PENDING;
	
	@Column
	private GoalCycle selectedDays;
}
