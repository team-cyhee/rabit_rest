
package com.cyhee.rabit.goallog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cyhee.rabit.cmm.model.TimestampEntity;
import com.cyhee.rabit.goal.model.GoalStatus;
import com.cyhee.rabit.user.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter @Getter @ToString
public class GoalLog extends TimestampEntity {
	
	@ManyToOne(optional=false)
	@JoinColumn(name="goal_id", foreignKey = @ForeignKey(name = "FK_GOAL_LOG"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User author;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable=false)
	private GoalStatus status = GoalStatus.PENDING;
}
