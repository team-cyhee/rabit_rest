package com.cyhee.rabit.cmm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false)
public class TimestampEntity extends BaseEntity{
	@CreationTimestamp
	@Column(nullable=false, updatable=false)
	private Date createDate;
	
	@UpdateTimestamp
	private Date lastUpdated;
}
