package com.cyhee.rabit.model.cs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cyhee.rabit.model.cmm.Content;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.user.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
public class Question extends TimestampEntity implements Content {

	@ManyToOne(optional=false)
	@JoinColumn(name="author_id", foreignKey = @ForeignKey(name = "FK_USER_QUESTION"))
	@OnDelete(action= OnDeleteAction.CASCADE)
	private User author;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(nullable=false)
	private ContentStatus status = ContentStatus.ACTIVE;
}
