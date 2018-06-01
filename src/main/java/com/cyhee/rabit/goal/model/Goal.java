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

import com.cyhee.rabit.cmm.model.TimestampEntity;
import com.cyhee.rabit.user.model.User;

@Entity
@Table
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
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Goal getParent() {
		return parent;
	}

	public void setParent(Goal parent) {
		this.parent = parent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Goal() {		
	}
	
	public Goal(User author, Goal parent, String content, Date startDate, Date endDate) {
		super();
		this.author = author;
		this.parent = parent;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
