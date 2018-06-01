package com.cyhee.rabit.goal.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.cyhee.rabit.user.model.User;

@Entity
@Table
public class Goal {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
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
	
	@CreationTimestamp
	@Column(nullable=false, updatable=false)
	private Date createDate;
	
	@UpdateTimestamp
	private Date lastUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
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
