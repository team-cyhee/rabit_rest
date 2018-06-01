package com.cyhee.rabit.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cyhee.rabit.goal.model.Goal;

@Entity
@Table
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique=true, length=50, updatable=false)
	private String email;
	
	@Column(nullable=false, length=255)
	private String password;
	
	@Column(nullable=false, unique=true, length=20)
	private String username;
	
	@Column(nullable=false, length=30)
	private String name;
		
	@Column(length=20)
	private String phone;
	
	@Temporal(TemporalType.DATE) 
	private Date birth;
	
	@CreationTimestamp
	@Column(nullable=false, updatable=false)
	private Date createDate;
	
	@UpdateTimestamp
	private Date lastUpdated;
	
	@Column(nullable=false)
	private UserStatus status = UserStatus.PENDING;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
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

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public User() {		
	}

	public User(String email, String password, String username, String name, String phone, Date birth) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.name = name;
		this.phone = phone;
		this.birth = birth;
	}
}