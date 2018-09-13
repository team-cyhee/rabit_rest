
package com.cyhee.rabit.model.goallog;

import javax.persistence.*;

import com.cyhee.rabit.model.file.FileInfo;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.goal.Goal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class GoalLog extends TimestampEntity {
	
	@ManyToOne(optional=false)
	@JoinColumn(name="goal_id", foreignKey = @ForeignKey(name = "FK_GOAL_LOG"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Goal goal;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable=false)
	private ContentStatus status = ContentStatus.ACTIVE;

	@ManyToMany
	private List<FileInfo> file;
}
