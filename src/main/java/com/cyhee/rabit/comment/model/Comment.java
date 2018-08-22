
package com.cyhee.rabit.comment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cyhee.rabit.cmm.model.ContentStatus;
import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.cmm.model.TimestampEntity;
import com.cyhee.rabit.user.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class Comment extends TimestampEntity {
	
	@ManyToOne(optional=false)
	@JoinColumn(name="author_id", foreignKey = @ForeignKey(name = "FK_COMMENT_AUTHOR"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User author;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ContentType type;
	
	@Column
	private Long parentId;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable=false)
	private ContentStatus status = ContentStatus.ACTIVE;
}
