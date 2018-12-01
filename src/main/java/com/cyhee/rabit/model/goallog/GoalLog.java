
package com.cyhee.rabit.model.goallog;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cyhee.rabit.model.cmm.Content;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.goal.Goal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
public class GoalLog extends TimestampEntity implements Content {
	
	@ManyToOne(optional=false)
	@JoinColumn(name="goal_id", foreignKey = @ForeignKey(name = "FK_GOAL_LOG"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Goal goal;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable=false)
	private ContentStatus status = ContentStatus.ACTIVE;

	@ManyToMany
	private List<FileInfo> files = new ArrayList<>();
}
