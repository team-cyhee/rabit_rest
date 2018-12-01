package com.cyhee.rabit.model.goal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cyhee.rabit.model.cmm.Content;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.user.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
public class Goal extends TimestampEntity implements Content {
	
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
	private ContentStatus status = ContentStatus.ACTIVE;
	
	@Column
	private GoalUnit doUnit;

	@Column
	private Integer doTimes;

	@ManyToMany
	private List<FileInfo> files = new ArrayList<>();
}
