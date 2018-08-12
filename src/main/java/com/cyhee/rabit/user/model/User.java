package com.cyhee.rabit.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.cyhee.rabit.cmm.model.TimestampEntity;
import com.cyhee.rabit.validation.Password;
import com.cyhee.rabit.validation.SetPasswordGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter @Getter @ToString
public class User extends TimestampEntity{
	
	@Column(nullable=false, unique=true, length=50, updatable=false)
	@Email
	@NotNull
	private String email;
	
	@Column(nullable=false, length=255)
	@Password(groups=SetPasswordGroup.class)
	@JsonView(UserView.UserPost.class)
	private String password;
	
	@Column(nullable=false, unique=true, length=20)
	@NotNull
	private String username;
	
	@Column(length=30)
	private String name;
		
	@Column(length=20)
	private String phone;
	
	@Temporal(TemporalType.DATE) 
	private Date birth;
	
	@Column(nullable=false)
	private UserStatus status = UserStatus.PENDING;

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