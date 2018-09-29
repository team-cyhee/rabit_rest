package com.cyhee.rabit.model.user;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.validation.Password;
import com.cyhee.rabit.validation.SetPasswordGroup;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name="rabit_user")
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class User extends TimestampEntity{
	
	@Column(nullable=false, unique=true, length=50, updatable=false)
	@Email
	@NotNull
	private String email;
	
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

	@ManyToMany
	private List<FileInfo> photo;
}