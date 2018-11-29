package com.cyhee.rabit.model.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.file.FileInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name="rabit_user")
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class User extends TimestampEntity{
	
	@Column(unique=true, length=50)
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
	private UserStatus status = UserStatus.ACTIVE;

	@ManyToMany
	private List<FileInfo> photo;
}